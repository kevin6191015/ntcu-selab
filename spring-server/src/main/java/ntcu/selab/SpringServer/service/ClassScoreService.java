package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.ClassScore;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.db.ClassScoreDBManager;

@RestController
@RequestMapping(value = "/ClassScore")
public class ClassScoreService {
    private static ClassScoreService  classScoreService = new ClassScoreService();
    private static ClassScoreDBManager classScoreDBManager = ClassScoreDBManager.getObject();

    public static ClassScoreService getObject(){
        return classScoreService;
    }

    @GetMapping("/getClassScore")
    public Result getClassScore(@RequestParam String semester, @RequestParam String class_id) throws Exception{
        List<JSONObject> Scorelist = new ArrayList<>();
        List<ClassScore> answereds = classScoreDBManager.getAnsweredBySemesterAndClassID(semester, class_id);
        List<ClassScore> corrects = classScoreDBManager.getCorrectBySemesterAndClassID(semester, class_id);
        try{
            for(int i=0;i<answereds.size()&&i<corrects.size();i++){
                JSONObject object = new JSONObject();
                object.put("people_answered", answereds.get(i).getPeopleAnswered());
                object.put("people_correct", corrects.get(i).getPeopleCorrect());
                Scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Answered numbers Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("ClassScore", Scorelist);
        return new Result(200, "Get Answered numbers Successfull!", root.toMap());
    }
    
    @GetMapping("/getAnsweredEveryday")
    public Result getAnsweredEveryday(@RequestParam String project_name) throws Exception{
        List<JSONObject> scorelist = new ArrayList<>();
        List<ClassScore> scores = classScoreDBManager.getAnsweredEveryday(project_name);
        try{
            for(ClassScore score : scores){
                JSONObject object = new JSONObject();
                object.put("day", score.getDay());
                object.put("answered_today", score.getAnsweredToday());
                scorelist.add(object);
            }     
        }catch(Exception e){
            return new Result(400, "Get Answered Everyday Failed! " + e.getMessage(), "");
        }   
        JSONObject root = new JSONObject();
        root.put("Answered Everyday", scorelist);
        return new Result(200, "Get Answered Everyday Successfull!", root.toMap());
    }
}
