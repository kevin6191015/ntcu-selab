package ntcu.selab.SpringServer.db;

import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.Course;

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

public class CourseDBManager {
    private static final Logger logger = LoggerFactory.getLogger(StudentDBManager.class);
    private static CourseDBManager dbManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null;

    public static CourseDBManager getObject(){
        if(dbManager == null){
            dbManager = new CourseDBManager();
        }
        return dbManager;
    }

    public List<Course> getAllCourses()throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "class");       
        List<Course> courses = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        System.out.println(jsonarray);  
        for (int i = 0; i < jsonarray.length(); i++) {
            Course c = new Course();
    		jsonobject = jsonarray.getJSONObject(i); 
            c.setId(String.valueOf(jsonobject.getInt("class_id")));
            c.setCourseName(jsonobject.getString("class_name"));
            c.setSemester(jsonobject.getString("semester"));
            c.setTeacher(jsonobject.getString("teacher"));
            c.setTA(jsonobject.getString("TA"));
            courses.add(c);
		}
        return courses;  
    }

    public List<Course> getCoursesBySemester(String semester)throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "class/" + semester);       
        List<Course> courses = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            Course c = new Course();
    		jsonobject = jsonarray.getJSONObject(i);      
            c.setId(String.valueOf(jsonobject.getInt("class_id")));     
            c.setCourseName(jsonobject.getString("class_name"));
            c.setSemester(jsonobject.getString("semester"));
            c.setTeacher(jsonobject.getString("teacher"));
            c.setTA(jsonobject.getString("TA"));
            courses.add(c);
		}
        return courses;  
    }

    public List<String> getSemester()throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "class/semester");       
        List<String> semesters = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        System.out.println(jsonarray); 
        for (int i = 0; i < jsonarray.length(); i++) {
    		jsonobject = jsonarray.getJSONObject(i);
            semesters.add(jsonobject.getString("semester"));
		}
        return semesters;  
    }
    
    public void addCourse(Course course) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "class/add");
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "class_name=" + course.getCourseName() + "&teacher=" + course.getTeacher() +
            "&TA=" + course.getTA() + "&semester=" + course.getSemester();
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

    public void updateCourse(String class_id, Course course) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "class/update/" + class_id);
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "class_name=" + course.getCourseName() + "&teacher=" + course.getTeacher() +
            "&TA=" + course.getTA();
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

    public void deleteCourse(String class_id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "class/delete/" + class_id);
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
}
