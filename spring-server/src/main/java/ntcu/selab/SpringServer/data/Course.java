package ntcu.selab.SpringServer.data;

public class Course {
    private String ID = null;
    private String CourseName = null;
    private String Semester = null;
    private String Teacher = null;
    private String TA = null;

    public Course(String coursename, String semester, String teacher, String TA){
        this.CourseName = coursename;
        this.Semester = semester;
        this.Teacher = teacher;
        this.TA = TA;
    }

    public Course(String coursename, String teacher, String TA){
        this.CourseName = coursename;
        this.Teacher = teacher;
        this.TA = TA;
    }

    public Course(){

    }

    public String getId(){
        return this.ID;
    }

    public void setId(String id){
        this.ID = id;
    }
    public String getCourseName(){
        return this.CourseName;
    }

    public void setCourseName(String coursename){
        this.CourseName = coursename;
    }

    public String getSemester(){
        return this.Semester;
    }

    public void setSemester(String semester){
        this.Semester = semester;
    }

    public String getTeacher(){
        return this.Teacher;
    }

    public void setTeacher(String teacher){
        this.Teacher= teacher;
    }

    public String getTA(){
        return this.TA;
    }

    public void setTA(String TA){
        this.TA = TA;
    }
}
