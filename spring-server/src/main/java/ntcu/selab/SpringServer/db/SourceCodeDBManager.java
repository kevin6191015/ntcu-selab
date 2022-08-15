package ntcu.selab.SpringServer.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.SourceCode;

public class SourceCodeDBManager {
    private static final Logger logger = LoggerFactory.getLogger(QuestionDBManager.class);
    private static SourceCodeDBManager scDbManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null;  

    public static SourceCodeDBManager getObject(){
        if(scDbManager == null){
            scDbManager = new SourceCodeDBManager();
        }
        return scDbManager;
    }

    public SourceCode getSourceCodeByName(String question_name) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "sourcecode/" + question_name);       
        
        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        SourceCode sourceCode = new SourceCode();
        sourceCode.setQuestionName(jsonobject.getString("question_name"));
        sourceCode.setCode(jsonobject.getString("code"));
        return sourceCode;
    }
    
    public Result addSourceCode(SourceCode sourceCode) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "sourcecode/add");
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "question_name=" + sourceCode.getQuestionName() + "&code=" + sourceCode.getCode();
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
            return new Result(400, "Add Source Code Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Add Source Code Sucessfull!", "");
    }

    public Result updateSourceCode(SourceCode sourceCode) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "sourcecode/update/" + sourceCode.getQuestionName());
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "code=" + sourceCode.getCode();
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
            return new Result(400, "Update Source Code Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Update Source Code Sucessfull!", "");
    }

    public Result deleteSourceCode(String question_name) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "sourcecode/delete/" + question_name);
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
            return new Result(400, "Delete Source Code Failed! " + e.getMessage(), "");
        }     
        return new Result(200, "Delete Source Code Sucessfull!", "");     
    }
}
