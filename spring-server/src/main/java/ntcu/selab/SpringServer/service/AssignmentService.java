package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Assignment;
import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.db.AssignmentDBManager;
import ntcu.selab.SpringServer.db.CourseDBManager;
import ntcu.selab.SpringServer.db.QuestionDBManager;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;

@RestController
@RequestMapping(value = "/assignment")
public class AssignmentService {
    private static AssignmentService object = new AssignmentService();
    private static AssignmentDBManager aDbManager = AssignmentDBManager.getObject();
    private static GitlabService gitlabService = GitlabService.getObject();
    private static JenkinsService jenkinsService = JenkinsService.getObject();
    private static QuestionDBManager qDbManager = QuestionDBManager.getObject();
    private static StudentDBManager sDbManager = StudentDBManager.getObject();
    private static UserDBManager uDbManager = UserDBManager.getObject();
    private static CourseDBManager cDbManager = CourseDBManager.getObject();

    public static AssignmentService getObject(){
        return object;
    }

    @GetMapping("getAssignments")
    public Result getAssignments(@RequestParam String cid)throws Exception{
        List<Assignment> assignments = aDbManager.getAllAssignment(cid);
        List<JSONObject> assignmeList = new ArrayList<>();
        try{
            for(Assignment assignment : assignments){
                JSONObject object = new JSONObject();
                object.put("question_id", assignment.getId());
                object.put("question_name", assignment.getName());
                object.put("release_time", assignment.getReleaseTime());
                object.put("deadline", assignment.getDeadLine());
                assignmeList.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Assignments Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Assignments", assignmeList);
        return new Result(200, "Get Assignments Successfull!", root.toMap());
    }
    
    @GetMapping("addAssignment")
    public Result addAssignment(@RequestParam String cid, @RequestParam String qid
    , @RequestParam String release_time, @RequestParam String deadline)throws Exception{
        try{
            /*
             * 將題目加進class_question裡
             */
            Question question = qDbManager.getQuestionFromBank1ById(qid);
            if(question == null){
                question = qDbManager.getQuestionFromBank2ById(qid);
            }
            Assignment assignment = new Assignment(qid, question.getName(), release_time, deadline);
            aDbManager.addAssignment(cid, assignment);
            /*
             * 對該班的student
             */
            List<Student> students = sDbManager.getStudents(cid);
            for(Student student : students){
                //得到project_name
                List<Course> courses = cDbManager.getAllCourses();
                String semester = null;
                for(Course course : courses){
                    if(course.getId() == cid){
                        semester = course.getSemester();
                        break;
                    }
                }
                String project_name = qid + "_" +  semester + "_" + release_time + "_" + student.getId();

                //創建jenkins project
                jenkinsService.createJob(project_name);

                //build jenkins job
                jenkinsService.buildJob(project_name);

                //創建gitlab project
                GitlabProject gitlabProject = gitlabService.createRootProject(project_name);

                //得到該學生在gitlab的個人資料
                GitlabUser gitlabUser = gitlabService.getUserById(Integer.valueOf(uDbManager.getUserInfo(student.getId()).getGitlabId()));

                //將學生加進該project
                gitlabService.addMember(gitlabUser, project_name);

                //設定gitlab使其與jenkins連接
                gitlabService.setGitlabIntegrations(gitlabProject, project_name);              
            }
        }catch(Exception e){
            return new Result(400, "Add Assignment Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Add Assignment Successfull!", "");
    }

    @GetMapping("updateAssignment")
    public Result updateAssignment(@RequestParam String cid, @RequestParam String qid
    , @RequestParam String release_time, @RequestParam String deadline)throws Exception{
        try{
            Assignment assignment = new Assignment(qid, release_time, deadline);
            aDbManager.updateAssignment(cid, assignment);
        }catch(Exception e){
            return new Result(400, "Update Assignment Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Update Assignment Successfull!", "");
    }

    @GetMapping("deleteAssignment")
    public Result deleteAssignment(@RequestParam String cid
    , @RequestParam String qid)throws Exception{
        try{
            /*
             * 對該班的student
             */
            List<Student> students = sDbManager.getStudents(cid);
            for(Student student : students){
                //得到project_name
                Question question = qDbManager.getQuestionFromBank1ById(qid);
                if(question == null){
                    question = qDbManager.getQuestionFromBank2ById(qid);
                }
                List<Course> courses = cDbManager.getAllCourses();
                String semester = null;
                String release = null;
                for(Course course : courses){
                    if(course.getId() == cid){
                        semester = course.getSemester();
                        break;
                    }
                }
                List<Assignment> assignments = aDbManager.getAllAssignment(cid);
                for(Assignment assignment : assignments){
                    if(assignment.getId() == qid){
                        release = assignment.getReleaseTime();
                        break;
                    }
                }
                String project_name = qid + "_" +  semester + "_" + release + "_" + student.getId();

                //刪除jenkins project
                jenkinsService.deleteJob(project_name);

                //刪除gitlab project
                gitlabService.deleteRootProject(project_name);
            }

            //將class_question裡的題目刪除
            aDbManager.deleteAssignment(cid, qid);
        }catch(Exception e){
            return new Result(400, "Delete Assignment Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Delete Assignment Successfull!", "");
    }
}
