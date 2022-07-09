package ntcu.selab.SpringServer.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.data.Question;

public class QuestionDBManager {
    private static QuestionDBManager dbManager = null;
    private static final Logger logger = LoggerFactory.getLogger(QuestionDBManager.class);
    private static final String dbUrl = "120.108.204.152:3000/api";

    public static QuestionDBManager getObject(){
        if(dbManager == null){
            dbManager = new QuestionDBManager();
        }
        return dbManager;
    }

    public List<Question> getAllQuestion() throws Exception{
        URL url = new URL(dbUrl + "question1");
        HttpURLConnection conn;
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        List<Question> questions = null;
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            if(status != HttpStatus.SC_OK){
                System.out.println("Connect failed with code: " + status);
            }
            BufferedReader br;
            String line;
            StringBuilder response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            jsonarray = new JSONArray( response.toString());
            jsonobject = jsonarray.getJSONObject(0);
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }       
        return questions;
    }

    public String getQuestionNameById(int id) throws Exception{
        URL url = new URL(dbUrl + "/qustion1/" + String.valueOf(id));
        HttpURLConnection conn;
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        String name = "";
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int status = conn.getResponseCode();
            if(status != HttpStatus.SC_OK){
                return "Connect failed with code: " + status;
            }
            BufferedReader br;
            String line;
            StringBuilder response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            jsonarray = new JSONArray( response.toString());
            jsonobject = jsonarray.getJSONObject(0);
            name = jsonobject.getString("question_name");
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
        return name;
    }
}
