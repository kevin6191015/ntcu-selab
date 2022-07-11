package ntcu.selab.SpringServer.db;

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
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.data.Question;

public class QuestionDBManager {
    private static final Logger logger = LoggerFactory.getLogger(QuestionDBManager.class);
    private static final String dbUrl = "http://120.108.204.152:3000/api";
    private static QuestionDBManager  dbManager = null;

    public static QuestionDBManager getObject(){
        if(dbManager == null){
            dbManager = new QuestionDBManager();
        }
        return dbManager;
    }

    public List<Question> getAllQuestion() throws Exception{
        URL url = new URL(dbUrl + "/question1");       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        List<Question> questions = new ArrayList<>();

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            Question s = new Question();
    		jsonobject = jsonarray.getJSONObject(i);
            s.setId(jsonobject.getString("id"));
            s.setName(jsonobject.getString("question_name"));
            s.setDescription(jsonobject.getString(("question_description")));
            s.setImage1(jsonobject.getString("image1"));
            s.setImage2(jsonobject.getString("image2"));
            String[] input = new String[11];
            String[] output = new String[11];
            for(int j=1 ; j<11 ; j++){
                input[j] = jsonobject.getString("input" + String.valueOf(j));
                output[j] = jsonobject.getString("output" + String.valueOf(j));
            }
            s.setInput(input);
            s.setOutput(output);
            s.setInputornot(jsonobject.getInt("input_or_not"));
            questions.add(s);
		}          
        return questions;
    }

    public Question getQuestionById(String id) throws Exception{
        URL url = new URL(dbUrl + "/question1/" + id);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        Question question = new Question();

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        question.setName(jsonobject.getString("question_name"));
        question.setDescription(jsonobject.getString("question_description"));
        question.setId(jsonobject.getString("id"));
        question.setName(jsonobject.getString("question_name"));
        question.setDescription(jsonobject.getString(("question_description")));
        question.setImage1(jsonobject.getString("image1"));
        question.setImage2(jsonobject.getString("image2"));
        String[] input = new String[11];
        String[] output = new String[11];
        for(int j=1 ; j<11 ; j++){
            input[j] = jsonobject.getString("input" + String.valueOf(j));
            output[j] = jsonobject.getString("output" + String.valueOf(j));
        }
        question.setInput(input);
        question.setOutput(output);
        question.setInputornot(jsonobject.getInt("input_or_not"));
        return question;
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
            //int status = conn.getResponseCode();
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
