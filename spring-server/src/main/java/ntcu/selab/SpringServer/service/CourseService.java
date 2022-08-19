package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Result;
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
    private static CourseDBManager cDbManager = CourseDBManager.getObject();
    private static QuestionDBManager qDbManager = QuestionDBManager.getObject();
    private static StudentDBManager sDbManager = StudentDBManager.getObject();
    private static UserDBManager uDbManager = UserDBManager.getObject();

    public static CourseService getObject(){
        return cs;
    }

    @GetMapping("getAllCourses")
    public Result getAllCourses() throws Exception{
        List<Course> courses = cDbManager.getAllCourses();
        List<JSONObject> courseList = new ArrayList<>();
        try{
            for(Course course : courses){
                JSONObject object = new JSONObject();
                object.put("class_name", course.getCourseName());
                object.put("semester", course.getSemester());
                object.put("teacher", course.getTeacher());
                object.put("TA", course.getTA());
                courseList.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Course Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Courses", courseList);
        return new Result(200, "Get Course Successfull!", root.toMap());
    }

    @GetMapping("getCoursesBySemester")
    public Result getCoursesBySemester(@RequestParam String semester) throws Exception{
        List<Course> courses = cDbManager.getCoursesBySemester(semester);
        List<JSONObject> courseList = new ArrayList<>();
        try{
            for(Course course : courses){
                JSONObject object = new JSONObject();
                object.put("class_name", course.getCourseName());
                object.put("semester", course.getSemester());
                object.put("teacher", course.getTeacher());
                object.put("TA", course.getTA());
                courseList.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Course Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Courses", courseList);
        return new Result(200, "Get Course Successfull!", root.toMap());
    }

    @GetMapping("getSemester")
    public Result getSemester() throws Exception{
        List<String> classes = cDbManager.getSemester();
        JSONObject root = new JSONObject();
        root.put("Semester", classes);
        return new Result(200, "Get Semesters Successfull!", root.toMap());
    }

    @GetMapping("addCourse")
    public Result addCourse(@RequestParam String class_name, @RequestParam String semester, 
    @RequestParam String teacher, @RequestParam String TA){
        try{
            Course course = new Course(class_name, semester, teacher, TA);
            cDbManager.addCourse(course);
        }catch(Exception e){
            return new Result(400, "Add Course Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Add Course Successfull!", "");
    }

    @GetMapping("updateCourse")
    public Result updateCourse(@RequestParam String cid, @RequestParam String class_name
    , @RequestParam String teacher, @RequestParam String TA){
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
            return new Result(400, "Update Course Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Update Course Successfull!", "");
    }

    @GetMapping("deleteCourse")
    public Result deleteCourse(@RequestParam String cid){
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
            return new Result(400, "Delete Course Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Delete Course Successfull!", "");
    }
}
