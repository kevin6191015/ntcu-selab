package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.Score;
import ntcu.selab.SpringServer.db.ScoreDBManager;

@RestController
@RequestMapping(value = "/score")
public class ScoreService {
    private static ScoreService scoreService = new ScoreService();
    private static ScoreDBManager scoreDBManager = ScoreDBManager.getObject();

    public static ScoreService getObject(){
        return scoreService;
    }

    @GetMapping("/getScore")
    public Result getScore(@RequestParam String project_name) throws Exception{
        List<JSONObject> scorelist = new ArrayList<>();
        List<Score> scores = scoreDBManager.getScoresByProjectname(project_name);
        try{
            for(Score score : scores){
                JSONObject object = new JSONObject();
                object.put("student_id", score.getStudentId());
                object.put("project_name", score.getProjectName());
                object.put("submit_times", score.getSubmitTimes());
                object.put("analysis_date", score.getAnalysisDate());
                object.put("unit_tes_score", score.getUnitTestScore());
                object.put("code_quality", score.getCodeQuality());
                scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Source Code Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Students", scorelist);
        return new Result(200, "Get Source Code Successfull!", root.toMap());
    }

    @GetMapping("/getLatestScore")
    public Result getLatestScore(@RequestParam String project_name) throws Exception{
        List<JSONObject> scorelist = new ArrayList<>();
        List<Score> scores = scoreDBManager.getLatestScore(project_name);
        try{
            for(Score score : scores){
                JSONObject object = new JSONObject();
                object.put("student_id", score.getStudentId());
                object.put("submit_times", score.getSubmitTimes());
                object.put("analysis_date", score.getAnalysisDate());
                object.put("unit_tes_score", score.getUnitTestScore());
                scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Source Code Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Scores", scorelist);
        return new Result(200, "Get Source Code Successfull!", root.toMap());
    }
}
