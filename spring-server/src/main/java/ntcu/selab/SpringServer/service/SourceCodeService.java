package ntcu.selab.SpringServer.service;

import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.SourceCode;
import ntcu.selab.SpringServer.db.SourceCodeDBManager;

@RestController
@RequestMapping(value = "/sourcecode")
public class SourceCodeService {
    private static SourceCodeService ss = new SourceCodeService();
    private static SourceCodeDBManager scDbManager = SourceCodeDBManager.getObject();

    public static SourceCodeService getObject(){
        return ss;
    }

    @GetMapping("/getSourceCode")
    public Result getSourceCode(@RequestParam String question_name) throws Exception{
        JSONObject object = new JSONObject();
        try{
            SourceCode sourceCode = scDbManager.getSourceCodeByName(question_name); 
            object.put("question_name", sourceCode.getQuestionName());
            object.put("code", sourceCode.getCode());
        }catch(Exception e){
            return new Result(400, "Get Source Code Failed! " + e.getMessage(), "");
        }   
        return new Result(200, "Get Source Code Successfull!", object.toMap());
    }

    @GetMapping("addSourceCode")
    public Result addSourceCode(@RequestParam String question_name
    , @RequestParam String filename) throws Exception{  
        try{
            InputStream is = this.getClass().getResourceAsStream("/" + filename);
            Scanner obj = new Scanner(is);
            String code = "";
            while (obj.hasNextLine()){
                code += obj.nextLine() +"\n";
            }
            obj.close();
            SourceCode sourceCode = new SourceCode();
            sourceCode.setQuestionName(question_name);
            sourceCode.setCode(code);
            scDbManager.addSourceCode(sourceCode);
        }catch(Exception e){
            return new Result(400, "Add Source Code Failed! " + e.getMessage(), "");
        }        
        return new Result(200, "Add Source Code Successfull!", "");
    }

    @GetMapping("updateSourceCode")
    public Result updateSourceCode(@RequestParam String question_name
    , @RequestParam String filename) throws Exception{
        try{   
            InputStream is = this.getClass().getResourceAsStream("/" + filename);
            Scanner obj = new Scanner(is);
            String code = "";
            while (obj.hasNextLine()){
                code += obj.nextLine();
            }
            obj.close();
            SourceCode sourceCode = new SourceCode();
            sourceCode.setQuestionName(question_name);
            sourceCode.setCode(code);           
            scDbManager.updateSourceCode(sourceCode);
        }catch(Exception e){
            return new Result(400, "Update Source Code Failed! " + e.getMessage(), "");
        }        
        return new Result(200, "Update Source Code Successfull!", "");
    }

    @GetMapping("deleteSourceCode")
    public Result deleteSourceCode(@RequestParam String question_name) throws Exception{
        try{              
            scDbManager.deleteSourceCode(question_name);
        }catch(Exception e){
            return new Result(400, "Delete Source Code Failed! " + e.getMessage(), "");
        }        
        return new Result(200, "Delete Source Code Successfull!", "");
    }
}
