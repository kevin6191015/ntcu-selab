package ntcu.selab.SpringServer.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assignment {
    private int id = 0;
    private String name = "";
    private Date createtime = null;
    private Date deadline = null;
    private Date releasetime = null;
    private String description = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createtime;
    }

    public void setCreateTime(Date createtime) {
        this.createtime = createtime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
