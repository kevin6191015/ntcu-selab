package ntcu.selab.SpringServer.data;

public class SonarqubeReport {
    private String compile_result = null;
    private String source_code = null;
    private String report_suggestion = null;
    private String bugs = null;
    private String vulnerabilities = null;
    private String code_smells = null;
    private int submit_times = 0;

    public SonarqubeReport(String compile_result, String source_code, String report_suggestion){
        this.compile_result = compile_result;
        this.source_code = source_code;
        this.report_suggestion = report_suggestion;
    }

    public SonarqubeReport(){
        
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
    public String getSuggestion(){
        return this.report_suggestion;
    }

    public void setSuggestion(String suggestion){
        this.report_suggestion = suggestion;
    }

    public String getBugs(){
        return this.bugs;
    }

    public void setBugs(String bugs){
        this.bugs = bugs;
    }
    public String getVulnerabilities(){
        return this.vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities){
        this.vulnerabilities = vulnerabilities;
    }
    public String getCode_smells(){
        return this.code_smells;
    }

    public void setCode_smells(String code_smells){
        this.code_smells = code_smells;
    }

    public int getSubmitTimes(){
        return this.submit_times;
    }

    public void setSubmitTimes(int submit_times){
        this.submit_times = submit_times;
    }
}
