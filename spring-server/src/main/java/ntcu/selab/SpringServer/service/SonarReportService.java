package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.SonarqubeReport;
import ntcu.selab.SpringServer.db.SonarqubeReportDBManager;

@RestController
@RequestMapping(value = "/SonarqubeReport")
public class SonarReportService {
    private static SonarReportService sonarReportService = new SonarReportService();
    private static SonarqubeReportDBManager sonarqubeReportDBManager = SonarqubeReportDBManager.getObject();

    public static SonarReportService getObject(){
        return sonarReportService;
    }

    @GetMapping("/getSonarqubeReport")
    public Result getPersonalReport(@RequestParam String project_name) throws Exception{
        List<JSONObject> scorelist = new ArrayList<>();
        List<SonarqubeReport> scores = sonarqubeReportDBManager.getPersonalReport(project_name);
        try{
            for(SonarqubeReport score : scores){
                JSONObject object = new JSONObject();
                object.put("compile_result", score.getCompileResult());
                object.put("source_code", score.getSourceCode());
                object.put("report_suggestion", score.getSuggestion());
                object.put("bugs", score.getBugs());
                object.put("vulnerabilities", score.getVulnerabilities());
                object.put("code_smells", score.getCode_smells());
                object.put("submit_times", score.getSubmitTimes());
                scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Sonarqube Report Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Sonarqube Report", scorelist);
        return new Result(200, "Get Sonarqube Report Successfull!", root.toMap());
    }
}
