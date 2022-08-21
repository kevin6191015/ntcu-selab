package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.data.User;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;

@RestController
@RequestMapping(value = "/student")
public class StudentService {
    public static StudentService ss = new StudentService();
    private static StudentDBManager sDbManager = StudentDBManager.getObject();
    private static UserDBManager uDbManager = UserDBManager.getObject();

    public static StudentService getObject(){
        return ss;
    }

    @GetMapping("/getStudents")
    public Result getStudents(@RequestParam String id) throws Exception{
        List<Student> students = sDbManager.getStudents(id);
        List<JSONObject> studentlist = new ArrayList<>();
        try{            
            for(Student student : students){
                JSONObject object = new JSONObject();
                object.put("student_id", student.getId());
                object.put("student_name", student.getName());
                studentlist.add(object);
            }           
        }catch(Exception e){
            return new Result(400, "Get Students Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Students", studentlist);
        return new Result(200, "Get Students Successfull!", root.toMap());
    }

    @GetMapping("addStudent")
    public Result addStudent(@RequestParam String cid
    , @RequestParam String uid) throws Exception{
        String uname = UserDBManager.getObject().getNameById(uid);
        Student student = new Student(uid, uname);

        String error = getErrorMessage(cid, student);
        if (error.isEmpty()) {
            try{  
                //加進student database            
                sDbManager.addStudent(cid, student);

                //將cid加入user的classes
                User user = uDbManager.getUserInfo(uid);
                uDbManager.addClasses(user, cid);
                uDbManager.updateUser(user);
                return new Result(200, "Add Students Successfull!", "");
            }catch(Exception e){
                return new Result(400, "Add Students Failed! " + e.getMessage(), "");
            }
        }     
        return new Result(400, "Add Students Failed! " + error, "");
    }

    // @GetMapping("updateStudent")
    // public ResponseEntity<Object> updateStudent(@RequestParam String class_id, @RequestParam String sid, @RequestParam String sname) {
    //     HttpHeaders header = new HttpHeaders();
    //     header.add("Content_Type", "application/json");
    //     try{
    //         Student student = new Student(sid, sname);
    //         sDbManager.updateStudent(class_id, student);
    //     }catch(Exception e){
    //         logger.error(e.getMessage());
    //         return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    //     return new ResponseEntity<Object>(header, HttpStatus.OK); 
    // }

    @GetMapping("deleteStudent")
    public Result deleteStudent(@RequestParam String cid, @RequestParam String uid){
        try{
            //刪除student資料(student database)
            sDbManager.deleteStudent(cid, uid);

            //刪除user database的classes欄位中該課程id
            User user = uDbManager.getUserInfo(uid);
            uDbManager.deleteClasses(user, cid);   
            uDbManager.updateUser(user);
        }catch(Exception e){
            return new Result(400, "Delete Students Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Delete Students Successfull!", "");
    }

    private String getErrorMessage(String cid, Student student) throws Exception{
        String errorMessage = "";
        if (sDbManager.checkStudentId(cid, student.getId())) {
            errorMessage = student.getName() + " : The duplicated name.";
        }
        return errorMessage;
    }
}
