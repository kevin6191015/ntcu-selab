package ntcu.selab.SpringServer.data;

public class Assignment {
    private String id = null;
    private String name = null;
    private String deadline = null;
    private String releasetime = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
