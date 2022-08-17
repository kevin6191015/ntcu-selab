package ntcu.selab.SpringServer.data;

public class SourceCode {
    private String question_name = null;
    private String code = null;

    public SourceCode(String question_name, String code){
        this.question_name = question_name;
        this.code = code;
    }

    public SourceCode(){

    }

    public String getQuestionName(){
        return question_name;
    }

    public void setQuestionName(String question_name){
        this.question_name = question_name;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }
}
