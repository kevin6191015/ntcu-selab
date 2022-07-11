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

    public void deleteQuestionById(String id) throws Exception{
        URL url = new URL(dbUrl + "/question1/delete/" + id);
        setConnect(url, "POST");          
    }

    public void addQuestion(Question q) throws Exception{
        URL url = new URL(dbUrl + "/question1/add");
        HttpURLConnection conn;
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
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
            conn.disconnect();
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
    }

    public void updateQuestion(String question_id, Question q) throws Exception{
        URL url = new URL(dbUrl + "/question1/update/" + question_id);
        HttpURLConnection conn;
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
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
            conn.disconnect();
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
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
