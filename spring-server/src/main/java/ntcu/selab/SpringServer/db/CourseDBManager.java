package ntcu.selab.SpringServer.db;

import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Student;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

public class CourseDBManager {
    private static final Logger logger = LoggerFactory.getLogger(StudentDBManager.class);
    private static final String dbUrl = "http://120.108.204.152:3000/api/class/";
    private static CourseDBManager dbManager = null;

    public static CourseDBManager getObject(){
        if(dbManager == null){
            dbManager = new CourseDBManager();
        }
        return dbManager;
    }

    public List<Course> getCourses()throws Exception{
        URL url = new URL(dbUrl);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;
        List<Course> courses = new ArrayList<>();

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0); 
        return courses;  
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
