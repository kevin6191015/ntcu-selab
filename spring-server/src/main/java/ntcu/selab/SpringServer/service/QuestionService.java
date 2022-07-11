package ntcu.selab.SpringServer.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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

import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.db.QuestionDBManager;

@RestController
@RequestMapping(value = "/question")
public class QuestionService {
    private static QuestionService qs = new QuestionService();
    private static final String dbUrl = "120.108.204.152:3000/api";
    private static QuestionDBManager qDbManager = QuestionDBManager.getObject();

    public static QuestionService getObject(){
        return qs;
    }

    @GetMapping("/getQuestions")
    public ResponseEntity<Object> getQuestions() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        
        List<Question> questions = qDbManager.getAllQuestion();
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
        return new ResponseEntity<Object>(root, header, HttpStatus.OK);
    }

    @GetMapping("/getQuestion")
    public ResponseEntity<Object> getQuestionNameById(@RequestParam("id") int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionById(id);

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

        return new ResponseEntity<Object>(object, header, HttpStatus.OK);
    }

    // @GetMapping("/deleteQuestion")
    // public String deleteQuestionById(@RequestParam("id") int id) throws Exception{
    //     URL url = new URL(dbUrl + "/qustion1/delete" + String.valueOf(id));       
    //     JSONArray jsonarray = null;
    //     JSONObject jsonobject = null;
    //     String name = "";

    //     StringBuilder response = setConnect(url, "POST");
    //     jsonarray = new JSONArray( response.toString());
    //     jsonobject = jsonarray.getJSONObject(0);
    //     name = jsonobject.getString("question_name");
    //     return name;
    // }

    
}
