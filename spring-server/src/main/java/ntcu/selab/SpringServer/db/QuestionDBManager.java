package ntcu.selab.SpringServer.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.Question;

public class QuestionDBManager {
    private static final Logger logger = LoggerFactory.getLogger(QuestionDBManager.class);
    private static QuestionDBManager  dbManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null;  

    public static QuestionDBManager getObject(){
        if(dbManager == null){
            dbManager = new QuestionDBManager();
        }
        return dbManager;
    }

    public List<Question> getAllQuestion() throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "question1");       
        
        List<Question> questions = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
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
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "question1/" + id);       

        Question question = new Question();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
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

    public void deleteQuestionById(String id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "question1/delete/" + id);
        try{
            conn = database.getConnection(url, "POST");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }            
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            conn.disconnect();
            jsonobject = new JSONObject(response.toString());           
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }          
    }

    public void addQuestion(Question q) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "question1/add");
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String[] input = q.getInput();
            String[] output = q.getOutnput();
            String info = "question_name=" + q.getName() + "&question_description=" + q.getDescription() +
            "&image1=" + q.getImage1() + "&image2=" + q.getImage2() + "&input_or_not=" + String.valueOf(q.getInputornot());
            for(int i=1 ; i<11 ; i++){
                info += "&input" + String.valueOf(i) + "=" + input[i];
                info += "&output" + String.valueOf(i) + "=" + output[i];
            }
            byte[] data = info.getBytes();
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            conn.disconnect();
            jsonobject = new JSONObject(response.toString());           
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
    }

    public void updateQuestion(String question_id, Question q) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "question1/update/" + question_id);
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String[] input = q.getInput();
            String[] output = q.getOutnput();
            String info = "question_name=" + q.getName() + "&question_description=" + q.getDescription() +
            "&image1=" + q.getImage1() + "&image2=" + q.getImage2() + "&input_or_not=" + String.valueOf(q.getInputornot());
            for(int i=1 ; i<11 ; i++){
                info += "&input" + String.valueOf(i) + "=" + input[i];
                info += "&output" + String.valueOf(i) + "=" + output[i];
            }
            byte[] data = info.getBytes();
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            conn.disconnect();
            jsonobject = new JSONObject(response.toString());
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
    }
}
