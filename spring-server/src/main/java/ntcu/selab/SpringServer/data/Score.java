package ntcu.selab.SpringServer.data;

public class Score {
    private String student_id = null;
    private String project_name = null;
    private int submit_times = 0;
    private String analysis_date = null;
    private int unit_test_score = 0;
    private int code_quality = 0;
    private String compile_result = null;
    private String source_code = null;

    public Score(String student_id, String project_name, int submit_times, String analysis_date, int unit_test_score, int code_quality){
        this.student_id = student_id;
        this.project_name = project_name;
        this.submit_times = submit_times;
        this.analysis_date = analysis_date;
        this.unit_test_score = unit_test_score;
        this.code_quality = code_quality;
    }

    public Score(String student_id, int submit_times, int unit_test_score, String analysis_date){
        this.student_id = student_id;
        this.submit_times = submit_times;
        this.analysis_date = analysis_date;
        this.unit_test_score = unit_test_score;
    }

    public Score(String compile_result, String source_code){
        this.compile_result = compile_result;
        this.source_code = source_code;
    }

    public Score(){

    }
    
    public String getStudentId(){
        return this.student_id;
    }

    public void setStudentId(String student_id){
        this.student_id = student_id;
    }

    public String getProjectName(){
        return this.project_name;
    }

    public void setProjectName(String project_name){
        this.project_name = project_name;
    }

    public int getSubmitTimes(){
        return this.submit_times;
    }

    public void setSubmitTimes(int submit_times){
        this.submit_times = submit_times;
    }

    public String getAnalysisDate(){
        return this.analysis_date;
    }

    public void setAnalysisDate(String analysis_date){
        this.analysis_date = analysis_date;
    }

    public int getUnitTestScore(){
        return this.unit_test_score;
    }

    public void setUnitTestScore(int unit_test_score){
        this.unit_test_score = unit_test_score;
    }

    public int getCodeQuality(){
        return this.code_quality;
    }

    public void setCodeQuality(int code_quality){
        this.code_quality = code_quality;
    }

    public String getCompileResult(){
        return this.compile_result;
    }

    public void setCompileResult(String compile_result){
        this.compile_result = compile_result;
    }

    public String getSourceCode(){
        return this.source_code;
    }

    public void setSourceCode(String source_code){
        this.source_code = source_code;
    }
}
