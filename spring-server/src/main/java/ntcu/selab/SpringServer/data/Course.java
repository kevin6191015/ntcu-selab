package ntcu.selab.SpringServer.data;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id = null;
    private String coursename = null;
    private String teacher = null;
    private String TA = null;
    private List<Student> studentlist = new ArrayList<>();
    private List<Assignment> assignmentlist = new ArrayList<>();

    public Course(String coursename, String teacher, String TA){
        this.coursename = coursename;
        this.teacher = teacher;
        this.TA = TA;
    }

    public Course(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getCourseName(){
        return coursename;
    }

    public void setCourseName(String coursename){
        this.coursename = coursename;
    }

    public String getTeacher(){
        return teacher;
    }

    public void setTeacher(String teacher){
        this.teacher = teacher;
    }

    public String getTA(){
        return TA;
    }

    public void setTA(String TA){
        this.TA = TA;
    }

    public List<Student> getStudents(){
        return studentlist;
    }

    public void setStudents(List<Student> students){
        for(Student student : students){
            studentlist.add(student);
        }
    }

    public List<Assignment> getAssigments(){
        return assignmentlist;
    }

    public void setAssignments(List<Assignment> assignments){
        for(Assignment assignment : assignments){
            assignmentlist.add(assignment);
        }
    }
}
