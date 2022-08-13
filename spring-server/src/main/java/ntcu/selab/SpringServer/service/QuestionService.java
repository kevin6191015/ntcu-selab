package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.db.QuestionDBManager;

@RestController
@RequestMapping(value = "/question")
public class QuestionService {
    private static QuestionService qs = new QuestionService();
    private static QuestionDBManager qDbManager = QuestionDBManager.getObject();

    public static QuestionService getObject(){
        return qs;
    }

    @GetMapping("/getQuestionBank1")
    public ResponseEntity<Object> getQuestionBank1() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        
        List<Question> questions = qDbManager.getQuestionBank1();
        List<JSONObject> questionlist = new ArrayList<>();
        for(Question question : questions){
            JSONObject object = new JSONObject();
            object.put("question_name", question.getName());
            object.put("question_description", question.getDescription());
            object.put("image1", question.getImage1());
            object.put("image2", question.getImage2());
            String[] input = question.getInput();
            String[] output = question.getOutnput();
            for(int i=0 ; i<10 ; i++){
                object.put("input" + String.valueOf(i), input[i]);
                object.put("output" + String.valueOf(i), output[i]);
            }
            object.put("inputornot", question.getInputornot());
            
            questionlist.add(object);
        }
        
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        return new ResponseEntity<Object>(root.toMap(), header, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getQuestionBank2")
    public ResponseEntity<Object> getQuestionBank2() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        
        List<Question> questions = qDbManager.getQuestionBank2();
        List<JSONObject> questionlist = new ArrayList<>();
        for(Question question : questions){
            JSONObject object = new JSONObject();
            object.put("id", question.getId());
            object.put("question_name", question.getName());
            object.put("question_description", question.getDescription());
            object.put("teacher", question.getTeacher());
            object.put("class_id", question.getClassId());
            object.put("image1", question.getImage1());
            object.put("image2", question.getImage2());
            String[] input = question.getInput();
            String[] output = question.getOutnput();
            for(int i=0 ; i<10 ; i++){
                object.put("input" + String.valueOf(i), input[i]);
                object.put("output" + String.valueOf(i), output[i]);
            }
            object.put("inputornot", question.getInputornot());
            
            questionlist.add(object);
        }
        
        List<String> teachers = qDbManager.getTeachers();
        List<String> classes = qDbManager.getClasses();
        
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        root.put("Teachers", teachers);
        root.put("Classes", classes);
        return new ResponseEntity<Object>(root.toMap(), header, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getQuestionFromBank1")
    public ResponseEntity<Object> getQuestionFromBank1(@RequestParam("id") String qid) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionFromBank1ById(qid);

        object.put("question_name", question.getName());
        object.put("question_description", question.getDescription());
        object.put("image1", question.getImage1());
        object.put("image2", question.getImage2());
        String[] input = question.getInput();
        String[] output = question.getOutnput();
        for(int i=0 ; i<10 ; i++){
            object.put("input" + String.valueOf(i), input[i]);
            object.put("output" + String.valueOf(i), output[i]);
        }
        object.put("inputornot", question.getInputornot());

        return new ResponseEntity<>(object.toMap(), header, HttpStatus.OK);
    }

    @GetMapping("/getQuestionFromBank2")
    public ResponseEntity<Object> getQuestionfromBank2(@RequestParam("id") String qid) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionFromBank2ById(qid);

        object.put("question_name", question.getName());
        object.put("question_description", question.getDescription());
        object.put("teacher", question.getTeacher());
        object.put("class_id", question.getClassId());
        object.put("image1", question.getImage1());
        object.put("image2", question.getImage2());
        String[] input = question.getInput();
        String[] output = question.getOutnput();
        for(int i=0 ; i<10 ; i++){
            object.put("input" + String.valueOf(i), input[i]);
            object.put("output" + String.valueOf(i), output[i]);
        }
        object.put("inputornot", question.getInputornot());

        return new ResponseEntity<Object>(object.toMap(), header, HttpStatus.OK);
    }

    @GetMapping("/deleteQuestion")
    public Result deleteQuestionById(@RequestParam("id") String id) throws Exception{
        return qDbManager.deleteQuestionById(id);
    }

    @GetMapping("/addQuestionToBank1")
    public Result addQuestionToBank1(@RequestBody Question question) throws Exception{
        return qDbManager.addQuestionbank1(question);
    }

    @PostMapping("/addQuestionToBank2")
    public Result addQuestionToBank2( @RequestBody Question question) throws Exception{
        return qDbManager.addQuestionbank2(question);
    }
    
    @GetMapping("/updateQuestion")
    public Result updateQuestion(@RequestBody Question question) throws Exception{
        return qDbManager.updateQuestion(question.getId(), question);
    }
}
