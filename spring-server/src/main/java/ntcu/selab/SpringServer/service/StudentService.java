package ntcu.selab.SpringServer.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.db.StudentDBManager;

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
    @ResponseBody
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
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        JSONObject root = new JSONObject();
        root.put("Questions", studentlist);
        return new ResponseEntity<Object>(root, header, HttpStatus.OK);
    }

    @GetMapping("addStudent")
    public ResponseEntity<Object> addStudent(@RequestParam String class_id, @RequestParam String sid, @RequestParam String sname) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            Student student = new Student(sid, sname);
            sDbManager.addStudent(class_id, student);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("updateStudent")
    public ResponseEntity<Object> updateStudent(@RequestParam String class_id, @RequestParam String sid, @RequestParam String sname) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            Student student = new Student(sid, sname);
            sDbManager.updateStudent(class_id, student);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK); 
    }

    @GetMapping("deleteStudent")
    public ResponseEntity<Object> deleteStudent(@RequestParam String class_id, @RequestParam String sid){
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");
        JSONObject jsonObject = null;
        try{
            jsonObject = sDbManager.deleteStudent(class_id, sid);
            header.add("Custom-Header,", "message");
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(jsonObject, header, HttpStatus.OK); 
    }
}
