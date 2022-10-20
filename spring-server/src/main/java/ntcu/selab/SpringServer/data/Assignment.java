package ntcu.selab.SpringServer.data;

public class Assignment {
    private String ID = null;
    private String AssignmentName = null;
    private String Name = null;
    private String CreatedTime = null;
    private String DeadLine = null;
    private String ReleaseTime = null;
    
    public Assignment(String id, String name, String assignmentname, String releasetime, String deadline, String createtime){
        this.ID = id;
        this.Name = name;
        this.AssignmentName = assignmentname;
        this.ReleaseTime = releasetime;
        this.DeadLine = deadline;
        this.CreatedTime = createtime;
    }

    public Assignment(){

    }

    public String getId() {
        return this.ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getAssignmentName(){
        return this.AssignmentName;
    }

    public void setAssignmentName(String assignmentname){
        this.AssignmentName = assignmentname;
    }
    
    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDeadLine() {
        return this.DeadLine;
    }

    public void setDeadLine(String deadline) {
        this.DeadLine = deadline;
    }

    public String getReleaseTime() {
        return this.ReleaseTime;
    }

    public void setReleaseTime(String releasetime) {
        this.ReleaseTime = releasetime;
    }

    public String getCreatedTime(){
        return this.CreatedTime;
    }

    public void setCreatedTime(String createtime){
        this.CreatedTime = createtime;
    }
}
