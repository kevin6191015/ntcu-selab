package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.db.QuestionDBManager;

@RestController
@RequestMapping(value = "/question")
public class QuestionService {
    private static QuestionService qs = new QuestionService();
    private static QuestionDBManager qDbManager = QuestionDBManager.getObject();

    public static QuestionService getObject(){
        return qs;
    }

    @GetMapping("/getQuestionBank1")
    public ResponseEntity<Object> getQuestionBank1() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        
        List<Question> questions = qDbManager.getQuestionBank1();
        List<JSONObject> questionlist = new ArrayList<>();
        for(Question question : questions){
            JSONObject object = new JSONObject();
            object.put("question_name", question.getName());
            object.put("question_description", question.getDescription());
            object.put("image1", question.getImage1());
            object.put("image2", question.getImage2());
            String[] input = question.getInput();
            String[] output = question.getOutnput();
            for(int i=0 ; i<10 ; i++){
                object.put("input" + String.valueOf(i), input[i]);
                object.put("output" + String.valueOf(i), output[i]);
            }
            object.put("inputornot", question.getInputornot());
            
            questionlist.add(object);
        }
        
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        return new ResponseEntity<Object>(root.toMap(), header, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getQuestionBank2")
    public ResponseEntity<Object> getQuestionBank2() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        
        List<Question> questions = qDbManager.getQuestionBank2();
        List<JSONObject> questionlist = new ArrayList<>();
        for(Question question : questions){
            JSONObject object = new JSONObject();
            object.put("id", question.getId());
            object.put("question_name", question.getName());
            object.put("question_description", question.getDescription());
            object.put("teacher", question.getTeacher());
            object.put("class_id", question.getClassId());
            object.put("image1", question.getImage1());
            object.put("image2", question.getImage2());
            String[] input = question.getInput();
            String[] output = question.getOutnput();
            for(int i=0 ; i<10 ; i++){
                object.put("input" + String.valueOf(i), input[i]);
                object.put("output" + String.valueOf(i), output[i]);
            }
            object.put("inputornot", question.getInputornot());
            
            questionlist.add(object);
        }
        
        List<String> teachers = qDbManager.getTeachers();
        List<String> classes = qDbManager.getClasses();
        
        JSONObject root = new JSONObject();
        root.put("Questions", questionlist);
        root.put("Teachers", teachers);
        root.put("Classes", classes);
        return new ResponseEntity<Object>(root.toMap(), header, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getQuestionFromBank1")
    public ResponseEntity<Object> getQuestionFromBank1(@RequestParam("id") String qid) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionFromBank1ById(qid);

        object.put("question_name", question.getName());
        object.put("question_description", question.getDescription());
        object.put("image1", question.getImage1());
        object.put("image2", question.getImage2());
        String[] input = question.getInput();
        String[] output = question.getOutnput();
        for(int i=0 ; i<10 ; i++){
            object.put("input" + String.valueOf(i), input[i]);
            object.put("output" + String.valueOf(i), output[i]);
        }
        object.put("inputornot", question.getInputornot());

        return new ResponseEntity<>(object.toMap(), header, HttpStatus.OK);
    }

    @GetMapping("/getQuestionFromBank2")
    public ResponseEntity<Object> getQuestionfromBank2(@RequestParam("id") String qid) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");
        JSONObject object = new JSONObject();
        Question question = qDbManager.getQuestionFromBank2ById(qid);

        object.put("question_name", question.getName());
        object.put("question_description", question.getDescription());
        object.put("teacher", question.getTeacher());
        object.put("class_id", question.getClassId());
        object.put("image1", question.getImage1());
        object.put("image2", question.getImage2());
        String[] input = question.getInput();
        String[] output = question.getOutnput();
        for(int i=0 ; i<10 ; i++){
            object.put("input" + String.valueOf(i), input[i]);
            object.put("output" + String.valueOf(i), output[i]);
        }
        object.put("inputornot", question.getInputornot());

        return new ResponseEntity<Object>(object.toMap(), header, HttpStatus.OK);
    }

    @GetMapping("/deleteQuestion")
    public ResponseEntity<Object> deleteQuestionById(@RequestParam("id") String id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        qDbManager.deleteQuestionById(id);
        return new ResponseEntity<Object>(header, HttpStatus.OK);
    }

    @GetMapping("/addQuestionToBank1")
    public ResponseEntity<Object> addQuestionToBank1(@RequestParam("id") String id, @RequestParam("question_name") String name
    , @RequestParam("question_description") String description, @RequestParam("input1") String input1, @RequestParam("input2") String input2
    , @RequestParam("input3") String input3, @RequestParam("input4") String input4, @RequestParam("input5") String input5
    , @RequestParam("input6") String input6, @RequestParam("input7") String input7, @RequestParam("input8") String input8
    , @RequestParam("input9") String input9, @RequestParam("input10") String input10, @RequestParam("output1") String output1
    , @RequestParam("output2") String output2, @RequestParam("output3") String output3, @RequestParam("output4") String output4
    , @RequestParam("output5") String output5, @RequestParam("output6") String output6, @RequestParam("output7") String output7
    , @RequestParam("output8") String output8, @RequestParam("output9") String output9, @RequestParam("output10") String output10
    , @RequestParam("image1") String image1, @RequestParam("image2") String image2, @RequestParam("input_or_not") int inputornot
    ) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        Question q = new Question();
        q.setName(name);
        q.setDescription(description);
        q.setImage1(image1);
        q.setImage2(image2);
        String[] input = new String[11];
        String[] output = new String[11];
        input[1] = input1;
        input[2] = input2;
        input[3] = input3;
        input[4] = input4;
        input[5] = input5;
        input[6] = input6;
        input[7] = input7;
        input[8] = input8;
        input[9] = input9;
        input[10] = input10;
        output[1] = output1;
        output[2] = output2;
        output[3] = output3;
        output[4] = output4;
        output[5] = output5;
        output[6] = output6;
        output[7] = output7;
        output[8] = output8;
        output[9] = output9;
        output[10] = output10;
        for(int i=1 ; i<11 ; i++){
            q.setInput(input);
            q.setOutput(output);
        }
        q.setInputornot(inputornot);
        qDbManager.addQuestionbank1(q);
        return new ResponseEntity<Object>(header, HttpStatus.OK);
    }

    /*
     * http://127.0.0.1:8081/question/addQuestion?id=a0052&input1=123&input2=223&input3=323&output1=121&output2=221&output3=321&input4=123&input5=123&
     * input6=123&input7=123&input8=123&input9=123&input10=123&output4=321&output5=321&output6=321&output7=321&output8=321&output9=321&output10=321&question_name=qwe
     * &question_description=tgfh&input_or_not=1&image1=www&image2=lll
     */
    @GetMapping("/addQuestionToBank2")
    public ResponseEntity<Object> addQuestionToBank2( @RequestParam("question_name") String name
    , @RequestParam("question_description") String description, @RequestParam("input1") String input1, @RequestParam("input2") String input2
    , @RequestParam("input3") String input3, @RequestParam("input4") String input4, @RequestParam("input5") String input5
    , @RequestParam("input6") String input6, @RequestParam("input7") String input7, @RequestParam("input8") String input8
    , @RequestParam("input9") String input9, @RequestParam("input10") String input10, @RequestParam("output1") String output1
    , @RequestParam("output2") String output2, @RequestParam("output3") String output3, @RequestParam("output4") String output4
    , @RequestParam("output5") String output5, @RequestParam("output6") String output6, @RequestParam("output7") String output7
    , @RequestParam("output8") String output8, @RequestParam("output9") String output9, @RequestParam("output10") String output10
    , @RequestParam("image1") String image1, @RequestParam("image2") String image2, @RequestParam("input_or_not") int inputornot
    , @RequestParam("teacher") String teacher, @RequestParam("class_id") String class_id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        Question q = new Question();
        q.setName(name);
        q.setDescription(description);
        q.setTeacher(teacher);
        q.setClassId(class_id);
        q.setImage1(image1);
        q.setImage2(image2);
        String[] input = new String[11];
        String[] output = new String[11];
        input[1] = input1;
        input[2] = input2;
        input[3] = input3;
        input[4] = input4;
        input[5] = input5;
        input[6] = input6;
        input[7] = input7;
        input[8] = input8;
        input[9] = input9;
        input[10] = input10;
        output[1] = output1;
        output[2] = output2;
        output[3] = output3;
        output[4] = output4;
        output[5] = output5;
        output[6] = output6;
        output[7] = output7;
        output[8] = output8;
        output[9] = output9;
        output[10] = output10;
        for(int i=1 ; i<11 ; i++){
            q.setInput(input);
            q.setOutput(output);
        }
        q.setInputornot(inputornot);
        qDbManager.addQuestionbank2(q);
        return new ResponseEntity<Object>(header, HttpStatus.OK);
    }
    
    /*
     * http://127.0.0.1:8081/question/updateQuestion?id=a0052&input1=qq&input2=223&input3=323&output1=121&output2=221&output3=321&input4=123&input5=123&
     * input6=123&input7=123&input8=123&input9=123&input10=123&output4=321&output5=321&output6=321&output7=321&output8=321&output9=321&output10=321
     */
    @GetMapping("/updateQuestion")
    public ResponseEntity<Object> updateQuestion(@RequestParam("id") String id, @RequestParam("input1") String input1
    , @RequestParam("input2") String input2, @RequestParam("input3") String input3, @RequestParam("input4") String input4
    , @RequestParam("input5") String input5, @RequestParam("input6") String input6, @RequestParam("input7") String input7
    , @RequestParam("input8") String input8, @RequestParam("input9") String input9, @RequestParam("input10") String input10
    , @RequestParam("output1") String output1, @RequestParam("output2") String output2, @RequestParam("output3") String output3
    , @RequestParam("output4") String output4, @RequestParam("output5") String output5, @RequestParam("output6") String output6
    , @RequestParam("output7") String output7, @RequestParam("output8") String output8, @RequestParam("output9") String output9
    , @RequestParam("output10") String output10, @RequestParam("teacher") String teacher, @RequestParam("class_id") String class_id
    , @RequestParam("input_or_not") int inputornot) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        Question q = new Question();
        String[] input = new String[11];
        String[] output = new String[11];
        input[1] = input1;
        input[2] = input2;
        input[3] = input3;
        input[4] = input4;
        input[5] = input5;
        input[6] = input6;
        input[7] = input7;
        input[8] = input8;
        input[9] = input9;
        input[10] = input10;
        output[1] = output1;
        output[2] = output2;
        output[3] = output3;
        output[4] = output4;
        output[5] = output5;
        output[6] = output6;
        output[7] = output7;
        output[8] = output8;
        output[9] = output9;
        output[10] = output10;
        for(int i=1 ; i<11 ; i++){
            q.setInput(input);
            q.setOutput(output);
        }
        qDbManager.updateQuestion(id, q);
        return new ResponseEntity<Object>(header, HttpStatus.OK);
    }
}
