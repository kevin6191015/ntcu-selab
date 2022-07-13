package ntcu.selab.SpringServer.db;

import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.Student;

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

public class StudentDBManager {
    private static final Logger logger = LoggerFactory.getLogger(StudentDBManager.class);
    private static StudentDBManager dbManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null;   

    public static StudentDBManager getObject(){
        if(dbManager == null){
            dbManager = new StudentDBManager();
        }
        return dbManager;
    }

    public List<Student> getStudents(String id)throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "student/" + id);               
            
        List<Student> students = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
		for (int i = 0; i < jsonarray.length(); i++) {
            Student s = new Student();
    		jsonobject = jsonarray.getJSONObject(i);
            s.setId(jsonobject.getString("student_id"));
            s.setName(jsonobject.getString("student_name"));
            students.add(s);
		}
        return students;  
    }

    public void addStudent(String class_id, Student student) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "student/add/" + class_id);
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "student_id=" + student.getId() + "&" + "student_name=" + student.getName();
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

    public void updateStudent(String class_id, Student student) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "student/update/" + class_id + "/" + student.getId());
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "student_id=" + student.getId() + "&" + "student_name=" + student.getName();
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

    public void deleteStudent(String class_id, String sid) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "student/delete/" + class_id + "/" + sid);
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

    public boolean checkStudentId(String cid, String uid) throws Exception{
        boolean check = false;
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "student/checkbyid/" + cid + "/" + uid);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        int count = jsonobject.getInt("count(*)");
        if(count >= 1){
            check = true;
        }
        return check;
    }
}
