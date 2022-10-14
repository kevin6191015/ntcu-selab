package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
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
    public Result getQuestionBank1() throws Exception{
        List<Question> questions = qDbManager.getQuestionBank1();
        List<JSONObject> questionlist = new ArrayList<>();
        try{
            for(Question question : questions){
                JSONObject object = new JSONObject();
                object.put("question_id", question.getId());
                object.put("question_name", question.getName());
                object.put("question_description", question.getDescription());
                object.put("image1", question.getImage1());
                object.put("image2", question.getImage2());
                String[] input = question.getInput();
                String[] output = question.getOutnput();
                for(int i=1 ; i<=10 ; i++){
                    object.put("input" + String.valueOf(i), input[i]);
                    object.put("output" + String.valueOf(i), output[i]);
                }
                object.put("inputornot", question.getInputornot());
                questionlist.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Questions From Bank1 Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        return new Result(200, "Get Questions From Bank1 Successfull!", root.toMap());
    }

    @GetMapping("/getQuestionBank2")
    public Result getQuestionBank2() throws Exception{ 
        List<Question> questions = qDbManager.getQuestionBank2();
        List<JSONObject> questionlist = new ArrayList<>();
        List<String> teachers = qDbManager.getTeachers();
        try{
            for(Question question : questions){
                JSONObject object = new JSONObject();
                object.put("id", question.getId());
                object.put("question_name", question.getName());
                object.put("question_description", question.getDescription());
                object.put("teacher", question.getTeacher());
                object.put("public_or_not", question.getPublicOrNot());
                object.put("image1", question.getImage1());
                object.put("image2", question.getImage2());
                String[] input = question.getInput();
                String[] output = question.getOutnput();
                for(int i=1 ; i<=10 ; i++){
                    object.put("input" + String.valueOf(i), input[i]);
                    object.put("output" + String.valueOf(i), output[i]);
                }
                object.put("inputornot", question.getInputornot());
                
                questionlist.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Questions From Bank2 Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        root.put("Teachers", teachers);
        return new Result(200, "Get Questions From Bank2 Successfull!", root.toMap());
    }

    @GetMapping("/getQuestionFromBank1")
    public Result getQuestionFromBank1(@RequestParam("id") String qid) throws Exception{
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionFromBank1ById(qid);
        try{
            object.put("question_name", question.getName());
            object.put("question_description", question.getDescription());
            object.put("image1", question.getImage1());
            object.put("image2", question.getImage2());
            String[] input = question.getInput();
            String[] output = question.getOutnput();
            for(int i=1 ; i<=10 ; i++){
                object.put("input" + String.valueOf(i), input[i]);
                object.put("output" + String.valueOf(i), output[i]);
            }
            object.put("inputornot", question.getInputornot());
        }catch(Exception e){
            return new Result(400, "Get Question From Bank1 Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Get Question From Bank1 Successfull!", object.toMap());
    }

    @GetMapping("/getQuestionFromBank2")
    public Result getQuestionfromBank2(@RequestParam("id") String qid) throws Exception{
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionFromBank2ById(qid);
        try{
            object.put("question_name", question.getName());
            object.put("question_description", question.getDescription());
            object.put("teacher", question.getTeacher());
            object.put("public_or_not", question.getPublicOrNot());
            object.put("image1", question.getImage1());
            object.put("image2", question.getImage2());
            String[] input = question.getInput();
            String[] output = question.getOutnput();
            for(int i=1 ; i<=10 ; i++){
                object.put("input" + String.valueOf(i), input[i]);
                object.put("output" + String.valueOf(i), output[i]);
            }
            object.put("inputornot", question.getInputornot());
        }catch(Exception e){
            return new Result(400, "Get Question From Bank2 Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Get Question From Bank2 Successfull!", object.toMap());
    }

    @GetMapping("/getPublicQuestion")
    public Result getPublicQuestion() throws Exception{ 
        List<Question> questions = qDbManager.getPublicQuestion();
        List<JSONObject> questionlist = new ArrayList<>();
        try{
            for(Question question : questions){
                JSONObject object = new JSONObject();
                object.put("id", question.getId());
                object.put("question_name", question.getName());
                object.put("question_description", question.getDescription());
                object.put("teacher", question.getTeacher());
                object.put("public_or_not", question.getPublicOrNot());
                object.put("image1", question.getImage1());
                object.put("image2", question.getImage2());
                String[] input = question.getInput();
                String[] output = question.getOutnput();
                for(int i=1 ; i<=10 ; i++){
                    object.put("input" + String.valueOf(i), input[i]);
                    object.put("output" + String.valueOf(i), output[i]);
                }
                object.put("inputornot", question.getInputornot());
                
                questionlist.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Public Questions Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        return new Result(200, "Get Public Questions Successfull!", root.toMap());
    }

    @GetMapping("/getQuestionsByTeacher")
    public Result getQuestionByTeacher(@RequestParam("teacher") String teacher) throws Exception{ 
        List<Question> questions = qDbManager.getQuestionsByTeacher(teacher);
        List<JSONObject> questionlist = new ArrayList<>();

        try{
            for(Question question : questions){
                JSONObject object = new JSONObject();
                object.put("id", question.getId());
                object.put("question_name", question.getName());
                object.put("question_description", question.getDescription());
                object.put("teacher", question.getTeacher());
                object.put("public_or_not", question.getPublicOrNot());
                object.put("image1", question.getImage1());
                object.put("image2", question.getImage2());
                String[] input = question.getInput();
                String[] output = question.getOutnput();
                for(int i=0 ; i<=10 ; i++){
                    object.put("input" + String.valueOf(i), input[i]);
                    object.put("output" + String.valueOf(i), output[i]);
                }
                object.put("inputornot", question.getInputornot());
                
                questionlist.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Questions By Teacher Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);

        return new Result(200, "Get Questions From Bank2 Successfull!", root.toMap());
    }

    @GetMapping("/deleteQuestion")
    public Result deleteQuestionById(@RequestParam("id") String id) throws Exception{
        try{
            qDbManager.deleteQuestionById(id);
        }catch(Exception e){
            return new Result(400, "Delete Question From Bank2 Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Delete Question From Bank2 Successfull!", "");
    }

    @GetMapping("/addQuestionToBank1")
    public Result addQuestionToBank1(@RequestBody Question question) throws Exception{
        try{
            qDbManager.addQuestionbank1(question);
        }catch(Exception e){
            return new Result(400, "Add Question to Bank1 Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Add Question to Bank1 Successfull!", "");
    }

    @PostMapping("/addQuestionToBank2")
    public Result addQuestionToBank2( @RequestBody Question question, @RequestParam int publicornot ) throws Exception{
        try{
            question.setPublicOrNot(publicornot);
            qDbManager.addQuestionbank2(question);
            System.out.println((question.getTeacher()));
        }catch(Exception e){
            return new Result(400, "Add Question to Bank2 Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Add Question to Bank2 Successfull!", "");
    }
    
    @GetMapping("/updateQuestion")
    public Result updateQuestion(@RequestBody Question question) throws Exception{
        try{
            qDbManager.updateQuestion(question.getId(), question);
        }catch(Exception e){
            return new Result(400, "Update Question From Bank2 Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Update Question From Bank2 Successfull!", "");
    }
}
