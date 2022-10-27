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
import ntcu.selab.SpringServer.data.SonarqubeReport;

public class SonarqubeReportDBManager {
    private static SonarqubeReportDBManager sonarqubeReportDBManager = null;
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null; 

    public static SonarqubeReportDBManager getObject(){
        if(sonarqubeReportDBManager == null){
            sonarqubeReportDBManager = new SonarqubeReportDBManager();
        }
        return sonarqubeReportDBManager;
    }

    public List<SonarqubeReport> getPersonalReport(String project_name) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "sqreport/getpersonalreport/" + project_name); 

        List<SonarqubeReport> scores = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
        response.append(line);
        br.close();
        jsonarray = new JSONArray( response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            SonarqubeReport score = new SonarqubeReport();
    		jsonobject = jsonarray.getJSONObject(i);
            score.setCompileResult(jsonobject.getString("compile_result"));
            score.setSourceCode(jsonobject.getString("source_code"));
            score.setSuggestion(jsonobject.getString("report_suggestion"));
            score.setBugs(jsonobject.getString("bugs"));
            score.setVulnerabilities(jsonobject.getString("vulnerabilities"));
            score.setCode_smells(jsonobject.getString("code_smells"));
            score.setSubmitTimes(jsonobject.getInt("submit_times"));
            scores.add(score);
		}
        return scores;
    }
}
