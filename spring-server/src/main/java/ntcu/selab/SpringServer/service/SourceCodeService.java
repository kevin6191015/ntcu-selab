package ntcu.selab.SpringServer.service;

import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.SourceCode;
import ntcu.selab.SpringServer.db.SourceCodeDBManager;

@RestController
@RequestMapping(value = "/sourcecode")
public class SourceCodeService {
    private static SourceCodeService ss = new SourceCodeService();
    private static SourceCodeDBManager scDbManager = SourceCodeDBManager.getObject();
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public static SourceCodeService getObject(){
        return ss;
    }

    @GetMapping("/getSourceCode")
    public ResponseEntity<Object> getSourceCode(@RequestParam String question_name) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        SourceCode sourceCode = scDbManager.getSourceCodeByName(question_name); 
        JSONObject object = new JSONObject();
        object.put("question_name", sourceCode.getQuestionName());
        object.put("code", sourceCode.getCode());
                      
        return new ResponseEntity<Object>(object.toMap(), header, HttpStatus.OK);
    }

    @GetMapping("addSourceCode")
    public ResponseEntity<Object> addSourceCode(@RequestParam String question_name
    , @RequestParam String filename) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");
        

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
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed! " + e.getMessage(), header, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("updateSourceCode")
    public ResponseEntity<Object> updateSourceCode(@RequestParam String question_name
    , @RequestParam String filename) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

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
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping("deleteSourceCode")
    public ResponseEntity<Object> deleteSourceCode(@RequestParam String question_name) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{              
            scDbManager.deleteSourceCode(question_name);
        }catch(Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
        return new ResponseEntity<>(header, HttpStatus.OK);
    }
}
