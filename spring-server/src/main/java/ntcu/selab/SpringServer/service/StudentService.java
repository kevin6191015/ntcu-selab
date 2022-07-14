package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;

@RestController
@RequestMapping(value = "/student")
public class StudentService {
    public static StudentService ss = new StudentService();
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static StudentDBManager sDbManager = StudentDBManager.getObject();

    public static StudentService getObject(){
        return ss;
    }

    @GetMapping("/getStudents")
    public ResponseEntity<Object> getStudents(@RequestParam String id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

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
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        JSONObject root = new JSONObject();
        root.put("Students", studentlist);
        return new ResponseEntity<Object>(root.toMap(), header, HttpStatus.OK);
    }

    @GetMapping("addStudent")
    public ResponseEntity<Object> addStudent(@RequestParam String cid
    , @RequestParam String uid) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");
        String uname = UserDBManager.getObject().getNameById(uid);
        Student student = new Student(uid, uname);

        String error = getErrorMessage(cid, student);
        if (error.isEmpty()) {
            try{              
                sDbManager.addStudent(cid, student);
            }catch(Exception e){
                logger.error(e.getMessage());
                return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<Object>(error, header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(header, HttpStatus.OK);
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
    public ResponseEntity<Object> deleteStudent(@RequestParam String class_id, @RequestParam String sid){
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");
        JSONObject jsonObject = null;
        try{
            sDbManager.deleteStudent(class_id, sid);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(jsonObject, header, HttpStatus.OK); 
    }

    private String getErrorMessage(String cid, Student student) throws Exception{
        String errorMessage = "";
        if (sDbManager.checkStudentId(cid, student.getId())) {
            errorMessage = student.getName() + " : The duplicated name.";
        }
        return errorMessage;
    }
}
