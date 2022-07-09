package ntcu.selab.SpringServer.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.data.Question;

@RestController
@RequestMapping(value = "/question")
public class QuestionService {
    private static QuestionService dbManager = null;
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private static final String dbUrl = "120.108.204.152:3000/api";

    public static QuestionService getObject(){
        if(dbManager == null){
            dbManager = new QuestionService();
        }
        return dbManager;
    }

    @GetMapping("/getQuestions")
    public List<Question> getAllQuestion() throws Exception{
        URL url = new URL(dbUrl + "question1");       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        List<Question> questions = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);            
        return questions;
    }

    @GetMapping("/getQuestion")
    public String getQuestionNameById(@RequestParam("id") int id) throws Exception{
        URL url = new URL(dbUrl + "/qustion1/" + String.valueOf(id));       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        String name = "";

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        name = jsonobject.getString("question_name");
        return name;
    }

    @GetMapping("/deleteQuestion")
    public String deleteQuestionById(@RequestParam("id") int id) throws Exception{
        URL url = new URL(dbUrl + "/qustion1/delete" + String.valueOf(id));       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        String name = "";

        StringBuilder response = setConnect(url, "POST");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        name = jsonobject.getString("question_name");
        return name;
    }

    public StringBuilder setConnect(URL url, String httpmethod) throws Exception{
        HttpURLConnection conn;
        StringBuilder response = null;
        BufferedReader br = null;
        String line = "";
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpmethod);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
        return response;
    }
}
