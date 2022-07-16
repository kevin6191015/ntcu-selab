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

import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.data.User;
import ntcu.selab.SpringServer.db.CourseDBManager;
import ntcu.selab.SpringServer.db.QuestionDBManager;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;

@RestController
@RequestMapping(value = "/course")
public class CourseService {
    public static CourseService cs = new CourseService();
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private static CourseDBManager cDbManager = CourseDBManager.getObject();
    private static QuestionDBManager qDbManager = QuestionDBManager.getObject();
    private static StudentDBManager sDbManager = StudentDBManager.getObject();
    private static UserDBManager uDbManager = UserDBManager.getObject();

    public static CourseService getObject(){
        return cs;
    }

    @GetMapping("getCourses")
    public ResponseEntity<Object> getCourses() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        List<Course> courses = cDbManager.getCourses();
        List<JSONObject> courseList = new ArrayList<>();
        try{
            for(Course course : courses){
                JSONObject object = new JSONObject();
                object.put("class_name", course.getCourseName());
                object.put("teacher", course.getTA());
                object.put("TA", course.getTA());
                courseList.add(object);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        JSONObject root = new JSONObject();
        root.put("Courses", courseList);
        return new ResponseEntity<Object>(root, header, HttpStatus.OK);
    }

    @GetMapping("addCourse")
    public ResponseEntity<Object> addCourse(@RequestParam String class_name, @RequestParam String teacher, @RequestParam String TA){
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            Course course = new Course(class_name, teacher, TA);
            cDbManager.addCourse(course);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("updateCourse")
    public ResponseEntity<Object> updateCourse(@RequestParam String cid, @RequestParam String class_name
    , @RequestParam String teacher, @RequestParam String TA){
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            //更新course資料
            Course course = new Course(class_name, teacher, TA);
            cDbManager.updateCourse(cid, course);

            //更新question裡course相關資料(question bank2)
            List<Question> questions = qDbManager.getQuestionsByClass(cid);
            for(Question question : questions){                
                question.setTeacher(teacher);
                qDbManager.updateQuestion(question.getId(), question);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("deleteCourse")
    public ResponseEntity<Object> deleteCourse(@RequestParam String cid){
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            //刪除user的classes中該課程的id
            List<Student> students = sDbManager.getStudents(cid);
            for(Student student : students){
                User user = uDbManager.getUserInfo(student.getId());
                uDbManager.deleteClasses(user, cid);
                uDbManager.updateUser(user);
            }
            
            //刪除course資料
            cDbManager.deleteCourse(cid);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK); 
    }
}
