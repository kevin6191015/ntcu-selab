package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
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

import ntcu.selab.SpringServer.data.Assignment;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.db.AssignmentDBManager;
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
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public static AssignmentService getObject(){
        return object;
    }

    @GetMapping("getAssignments")
    public ResponseEntity<Object> getAssignments(@RequestParam String cid)throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

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
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        JSONObject root = new JSONObject();
        root.put("Courses", assignmeList);
        return new ResponseEntity<Object>(root.toMap(), header, HttpStatus.OK);
    }
    
    @GetMapping("addAssignment")
    public ResponseEntity<Object> addAssignment(@RequestParam String cid, @RequestParam String qid
    , @RequestParam String release_time, @RequestParam String deadline)throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

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
                //設定project name
                String project_name = student.getId() + "_" + question.getName().replace(" ", "");

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
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("updateAssignment")
    public ResponseEntity<Object> updateAssignment(@RequestParam String cid, @RequestParam String qid
    , @RequestParam String release_time, @RequestParam String deadline)throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            Assignment assignment = new Assignment(qid, release_time, deadline);
            aDbManager.updateAssignment(cid, assignment);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("deleteAssignment")
    public ResponseEntity<Object> deleteAssignment(@RequestParam String cid
    , @RequestParam String qid)throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

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
                String project_name = student.getId() + "_"  + question.getName().replace(" ", "");

                //刪除jenkins project
                jenkinsService.deleteJob(project_name);

                //刪除gitlab project
                gitlabService.deleteRootProject(project_name);
            }

            //將class_question裡的題目刪除
            aDbManager.deleteAssignment(cid, qid);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }
}
