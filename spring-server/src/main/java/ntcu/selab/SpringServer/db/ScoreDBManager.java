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
import ntcu.selab.SpringServer.data.Score;

public class ScoreDBManager {
    private static ScoreDBManager scoreDBManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null; 

    public static ScoreDBManager getObject(){
        if(scoreDBManager == null){
            scoreDBManager= new ScoreDBManager();
        }
        return scoreDBManager;
    }

    public List<Score> getScoresByProjectname(String project_name) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/" + project_name); 

        List<Score> scores = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            Score score = new Score();
    		jsonobject = jsonarray.getJSONObject(i);
            score.setStudentId(jsonobject.getString("student_id"));
            score.setProjectName(jsonobject.getString("project_name"));
            score.setSubmitTimes(jsonobject.getInt("submit_times"));
            score.setAnalysisDate(jsonobject.getString("analysis_date"));
            score.setUnitTestScore(jsonobject.getInt("unit_test_score"));
            score.setCodeQuality(jsonobject.getInt("code_quality"));
            scores.add(score);
		}
        return scores;
    }

    public List<Score> getLatestScore(String project_name) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/geteveryonescore/" + project_name); 

        List<Score> scores = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            Score score = new Score();
    		jsonobject = jsonarray.getJSONObject(i);
            score.setStudentId(jsonobject.getString("student_id"));
            score.setSubmitTimes(jsonobject.getInt("submit_times"));
            score.setAnalysisDate(jsonobject.getString("analysis_date"));
            score.setUnitTestScore(jsonobject.getInt("unit_test_score"));
            scores.add(score);
		}
        return scores;
    }

    public List<Integer> getAnsweredBySemesterAndClassID(String semester, String class_id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/getpeopleanswered/" + semester + "/" + class_id); 

        List<Integer> answeredlist = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        System.out.println(jsonarray);
        for (int i = 0; i < jsonarray.length(); i++) {
    		jsonobject = jsonarray.getJSONArray(i).getJSONObject(0);
            answeredlist.add(jsonobject.getInt("people_answered"));
		}
        return answeredlist;
    }

    public List<Integer> getCorrectBySemesterAndClassID(String semester, String class_id) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "score/getpeoplecorrect/" + semester + "/" + class_id); 

        List<Integer> answeredlist = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        System.out.println(jsonarray);
        for (int i = 0; i < jsonarray.length(); i++) {
    		jsonobject = jsonarray.getJSONArray(i).getJSONObject(0);
            answeredlist.add(jsonobject.getInt("people_correct"));
		}
        return answeredlist;
    }
}
