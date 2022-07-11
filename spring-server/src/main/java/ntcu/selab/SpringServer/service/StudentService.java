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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.db.StudentDBManager;

@RestController
@RequestMapping(value = "/student")
public class StudentService {
    public static StudentService ss = new StudentService();
    private static final String dbUrl = "http://120.108.204.152:3000/api/student/";
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static StudentDBManager sDbManager = StudentDBManager.getObject();

    public static StudentService getObject(){
        return ss;
    }

    @GetMapping("/getStudents")
    public ResponseEntity<Object> getStudents(@RequestParam int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        List<Student> students = sDbManager.getStudents(id);
        List<JSONObject> studentlist = new ArrayList<>();
        for(Student student : students){
            JSONObject object = new JSONObject();
            object.put("student_id", student.getId());
            object.put("student_name", student.getName());
            studentlist.add(object);
        }
        JSONObject root = new JSONObject();
        root.put("Questions", studentlist);
        return new ResponseEntity<Object>(root, header, HttpStatus.OK);
    }

    @GetMapping("addStudent")
    public ResponseEntity<Object> addStudent(@RequestParam String id, @RequestParam String sid, @RequestParam String sname) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        HttpURLConnection conn;
        URL url = new URL(dbUrl + "/add/" + String.valueOf(id));
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "student_id=" + sid + "&" + "student_name=" + sname;
            byte[] data = info.getBytes();
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            conn.disconnect();
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
            return new ResponseEntity<Object>("Failed", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(header, HttpStatus.OK);
    }
}
