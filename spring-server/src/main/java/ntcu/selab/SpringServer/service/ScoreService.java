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
            return new Result(400, "Get Score Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Students", scorelist);
        return new Result(200, "Get Score Successfull!", root.toMap());
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
            return new Result(400, "Get Latest Score Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Scores", scorelist);
        return new Result(200, "Get Latest Score Successfull!", root.toMap());
    }

    @GetMapping("/getAnswered")
    public Result getAnswered(@RequestParam String semester, @RequestParam String class_id) throws Exception{
        List<JSONObject> answeredlist = new ArrayList<>();
        List<Integer> answereds = scoreDBManager.getAnsweredBySemesterAndClassID(semester, class_id);
        try{
            for(Integer answered : answereds){
                JSONObject object = new JSONObject();
                object.put("people_answered", answered);
                answeredlist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Answered numbers Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Answered", answeredlist);
        return new Result(200, "Get Answered numbers Successfull!", root.toMap());
    }

    @GetMapping("/getCorrect")
    public Result getCorrect(@RequestParam String semester, @RequestParam String class_id) throws Exception{
        List<JSONObject> answeredlist = new ArrayList<>();
        List<Integer> answereds = scoreDBManager.getCorrectBySemesterAndClassID(semester, class_id);
        try{
            for(Integer answered : answereds){
                JSONObject object = new JSONObject();
                object.put("people_correct", answered);
                answeredlist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Correct numbers Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Correct", answeredlist);
        return new Result(200, "Get Correct numbers Successfull!", root.toMap());
    }

    @GetMapping("/getPersonalScore")
    public Result getPersonalScore(@RequestParam String project_name) throws Exception{
        List<JSONObject> scorelist = new ArrayList<>();
        List<Score> scores = scoreDBManager.getPersonalScore(project_name);
        try{
            for(Score score : scores){
                JSONObject object = new JSONObject();
                object.put("code_quality", score.getCodeQuality());
                object.put("analysis_date", score.getAnalysisDate());
                object.put("unit_test_score", score.getUnitTestScore());
                scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Personal Score Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Personal Score", scorelist);
        return new Result(200, "Get Personal Score Successfull!", root.toMap());
    }

    @GetMapping("/getPersonalReport")
    public Result getPersonalReport(@RequestParam String project_name) throws Exception{
        List<JSONObject> scorelist = new ArrayList<>();
        List<Score> scores = scoreDBManager.getPersonalReport(project_name);
        try{
            for(Score score : scores){
                JSONObject object = new JSONObject();
                object.put("compile_result", score.getCompileResult());
                object.put("source_code", score.getSourceCode());
                scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Personal Report Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Personal Report", scorelist);
        return new Result(200, "Get Personal Report Successfull!", root.toMap());
    }

    @GetMapping("/getGitUrl")
    public Result getGitUrl(@RequestParam String username, @RequestParam String project_name) throws Exception{  
        String url = null;
        try{
            url = GitlabService.getObject().getProjectUrl(username, project_name);
        }catch(Exception e){
            return new Result(400, "Get Git Url Failed! " + e.getMessage(), "");
        }   
        return new Result(200, "Get Git Url Successfull!", url);
    }
}
