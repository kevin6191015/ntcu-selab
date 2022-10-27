package ntcu.selab.SpringServer.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.ClassScore;

public class ClassScoreDBManager {
    private static ClassScoreDBManager classScoreDBManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null; 

    public static ClassScoreDBManager getObject(){
        if(classScoreDBManager == null){
            classScoreDBManager= new ClassScoreDBManager();
        }
        return classScoreDBManager;
    }

    public List<ClassScore> getAnsweredEveryday(String project_name) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/getpeopleanswereveryday/" + project_name); 

        List<ClassScore> scores = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            ClassScore score = new ClassScore();
    		jsonobject = jsonarray.getJSONObject(i);
            score.setDay(jsonobject.getString("day"));
            score.setAnsweredToday(jsonobject.getInt("answered_today"));
            scores.add(score);
		}
        return scores;
    }

    public List<ClassScore> getAnsweredBySemesterAndClassID(String semester, String class_id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/getpeopleanswered/" + semester + "/" + class_id); 

        List<ClassScore> answeredlist = new ArrayList<>();

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
            ClassScore score = new ClassScore();
            score.setPeopleAnswered(jsonobject.getInt("people_answered"));
            answeredlist.add(score);
		}
        return answeredlist;
    }

    public List<ClassScore> getCorrectBySemesterAndClassID(String semester, String class_id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/getpeoplecorrect/" + semester + "/" + class_id); 

        List<ClassScore> answeredlist = new ArrayList<>();

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
            ClassScore score = new ClassScore();
            score.setPeopleCorrect(jsonobject.getInt("people_correct"));
            answeredlist.add(score);
		}
        return answeredlist;
    }
}
