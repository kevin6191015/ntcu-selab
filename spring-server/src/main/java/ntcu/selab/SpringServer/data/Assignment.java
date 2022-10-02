package ntcu.selab.SpringServer.data;

public class Assignment {
    private String id = null;
    private String assignment_name = null;
    private String name = null;
    private String deadline = null;
    private String releasetime = null;
    
    public Assignment(String id, String name, String assignment_name, String releasetime, String deadline){
        this.id = id;
        this.name = name;
        this.assignment_name = assignment_name;
        this.releasetime = releasetime;
        this.deadline = deadline;
    }

    public Assignment(String id, String releasetime, String deadline){
        this.id = id;
        this.releasetime = releasetime;
        this.deadline = deadline;
    }
    
    public Assignment(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentName(){
        return this.assignment_name;
    }

    public void setAssignmentName(String assignment_name){
        this.assignment_name = assignment_name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeadLine() {
        return deadline;
    }

    public void setDeadLine(String deadline) {
        this.deadline = deadline;
    }

    public String getReleaseTime() {
        return releasetime;
    }

    public void setReleaseTime(String releasetime) {
        this.releasetime = releasetime;
    }
}
