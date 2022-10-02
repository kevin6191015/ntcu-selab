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

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.Assignment;

public class AssignmentDBManager {
    private static AssignmentDBManager dbManager = new AssignmentDBManager();
    private static final Logger logger = LoggerFactory.getLogger(AssignmentDBManager.class);
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null;   

    public static AssignmentDBManager getObject() {
        return dbManager;
    }

    private AssignmentDBManager() {

    }

    public List<Assignment> getAllAssignment(String class_id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "classquestion/" + class_id); 

        List<Assignment> assignments = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            Assignment assignment = new Assignment();
    		jsonobject = jsonarray.getJSONObject(i);
            assignment.setId(jsonobject.getString("question_id"));
            assignment.setAssignmentName(jsonobject.getString("assignment_name"));
            assignment.setName(jsonobject.getString("question_name"));
            assignment.setReleaseTime(jsonobject.getString("release_time"));
            assignment.setDeadLine(jsonobject.getString("deadline"));
            assignments.add(assignment);
		}
        return assignments;
    }

    public void addAssignment(String cid, Assignment assignment) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "classquestion/add/" + cid);
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "question_id=" + assignment.getId() + "&assignment_name=" + assignment.getAssignmentName()
            + "&question_name=" + assignment.getName() + "&release_time=" + assignment.getReleaseTime() + "&deadline=" + assignment.getDeadLine();
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
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public void updateAssignment(String cid, Assignment assignment)throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "classquestion/update/" + cid + "/" + assignment.getId());
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "release_time=" + assignment.getReleaseTime() + "&deadline=" + assignment.getDeadLine();
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
        }catch(Exception e){
            logger.error(e.getMessage());
        }

    }

    public void deleteAssignment(String cid, String qid) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "classquestion/delete/" + cid + "/" + qid);
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
        }catch(Exception e){
            logger.error(e.getMessage());
        }  
    }
}
