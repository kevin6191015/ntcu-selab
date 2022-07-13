package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Assignment;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.db.AssignmentDBManager;
import ntcu.selab.SpringServer.db.QuestionDBManager;

@RestController
@RequestMapping(value = "/assignment")
public class AssignmentService {
    private static AssignmentService object = new AssignmentService();
    private static AssignmentDBManager aDbManager = AssignmentDBManager.getObject();
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
        return new ResponseEntity<Object>(root, header, HttpStatus.OK);
    }
    
    @GetMapping("addAssignment")
    public ResponseEntity<Object> addAssignment(@RequestParam String cid, @RequestParam String qid
    , @RequestParam String release_time, @RequestParam String deadline)throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            QuestionDBManager qDbManager = QuestionDBManager.getObject();
            Question question = qDbManager.getQuestionById(qid);
            Assignment assignment = new Assignment(qid, question.getName(), release_time, deadline);
            aDbManager.addAssignment(cid, assignment);
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
            aDbManager.deleteAssignment(cid, qid);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK);
    }
}
