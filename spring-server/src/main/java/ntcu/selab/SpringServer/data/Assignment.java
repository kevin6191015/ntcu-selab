package ntcu.selab.SpringServer.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assignment {
    private String id = "";
    private String name = "";
    private Date deadline = null;
    private Date releasetime = null;

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

    public Date getDeadLine() {
        return deadline;
    }

    public void setDeadLine(Date deadline) {
        this.deadline = deadline;
    }

    public Date getReleaseTime() {
        return releasetime;
    }

    public void setReleaseTime(Date releasetime) {
        this.releasetime = releasetime;
    }
}
