package ntcu.selab.SpringServer.service;

import java.util.ArrayList;
import java.util.List;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Assignment;
import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.db.AssignmentDBManager;
import ntcu.selab.SpringServer.db.CourseDBManager;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;

@RestController
@RequestMapping(value = "/assignment")
public class AssignmentService {
    private static AssignmentService object = new AssignmentService();
    private static AssignmentDBManager aDbManager = AssignmentDBManager.getObject();
    private static GitlabService gitlabService = GitlabService.getObject();
    private static JenkinsService jenkinsService = JenkinsService.getObject();
    private static StudentDBManager sDbManager = StudentDBManager.getObject();
    private static UserDBManager uDbManager = UserDBManager.getObject();
    private static CourseDBManager cDbManager = CourseDBManager.getObject();

    public static AssignmentService getObject(){
        return object;
    }

    @GetMapping("getAssignments")
    public Result getAssignments(@RequestParam String cid)throws Exception{
        List<Assignment> assignments = aDbManager.getAllAssignment(cid);
        List<JSONObject> assignmeList = new ArrayList<>();
        try{
            for(Assignment assignment : assignments){
                JSONObject object = new JSONObject();
                object.put("question_id", assignment.getId());
                object.put("assignment_name", assignment.getAssignmentName());
                object.put("question_name", assignment.getName());
                object.put("release_time", assignment.getReleaseTime());
                object.put("created_time", assignment.getCreatedTime());
                object.put("deadline", assignment.getDeadLine());
                assignmeList.add(object);
            }
        }catch(Exception e){
            return new Result(400, "Get Assignments Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Assignments", assignmeList);
        return new Result(200, "Get Assignments Successfull!", root.toMap());
    }
    //mvn archetype:generate -DgroupId=ntcu.selab -DartifactId=a0001_110_2_20220527_a001 -DinteractiveMode=false
    @PostMapping("addAssignment")
    public Result addAssignment(@RequestParam String cid, @RequestBody Assignment assignment)throws Exception{
        try{
            
            /*
             * 將題目加進class_question裡
             */
            aDbManager.addAssignment(cid, assignment);

            //得到完整cid
            String classID="";
            for (int i = 0; i < 3 - cid.length(); i++) {
                classID += '0';
            }
            classID+=cid;
      
            /*
             * 對該班的student
             */
            List<Course> courses = cDbManager.getAllCourses();
            String semester = null;
            for(Course course : courses){
                if(course.getId() .equals(cid)){
                    semester = course.getSemester();
                    break;
                }
            }
            List<Student> students = sDbManager.getStudents(cid);
            for(Student student : students){
                //得到project_name
                String project_name = assignment.getId() + "_" +classID +"_" +  semester + "_" + assignment.getCreatedTime() +"_" + student.getId();

                //創建gitlab project
                GitlabProject gitlabProject = gitlabService.createRootProject(project_name);

                //得到該學生在gitlab的個人資料
                GitlabUser gitlabUser = gitlabService.getUserById(Integer.valueOf(uDbManager.getUserInfo(student.getId()).getGitlabId()));

                //將學生加進該project
                gitlabService.addMember(gitlabUser, project_name);

                //clone 此次建立的 gitlab project
                gitlabService.cloneProject(gitlabUser.getUsername(),project_name,".\\src\\main\\resources\\maven\\"+project_name);

                //copy project內容 加入src跟.validate
                gitlabService.copyProject(project_name);

                //push 此次建立的 gitlab project
                gitlabService.pushProject(".\\src\\main\\resources\\maven\\"+project_name,project_name);

                //delete 此次建立的 gitlab project
                gitlabService.DeleteProject(project_name);

                //設定gitlab使其與jenkins連接(必須最後設定不然會直接執行)
                gitlabService.setGitlabIntegrations(gitlabProject, project_name);

                //創建jenkins project
                jenkinsService.createJob(project_name);

            }
        }catch(Exception e){
            return new Result(400, "Add Assignment Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Add Assignment Successfull!", "");
    }

    @PostMapping("updateAssignment")
    public Result updateAssignment(@RequestParam String cid, @RequestBody Assignment assignment)throws Exception{
        try{
            aDbManager.updateAssignment(cid, assignment);
        }catch(Exception e){
            return new Result(400, "Update Assignment Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Update Assignment Successfull!", "");
    }

    @GetMapping("deleteAssignment")
    public Result deleteAssignment(@RequestParam String cid
    , @RequestParam String qid, @RequestParam String created_time)throws Exception{
        try{
            /*
             * 對該班的student
             */
            List<Student> students = sDbManager.getStudents(cid);
            for(Student student : students){
                
                List<Course> courses = cDbManager.getAllCourses();
                String semester = null;

                //得到semester
                for(Course course : courses){
                    if(course.getId().equals(cid)){
                        semester = course.getSemester();
                        break;
                    }
                }

                //得到CID
                String classID = "";
                for (int i = 0; i < 3 - cid.length(); i++) {
                    classID += '0';
                }
                classID += cid;

                //得到project_name
                String project_name = qid + "_" + classID + "_" +  semester + "_" + created_time + "_" + student.getId();

                //刪除jenkins project
                jenkinsService.deleteJob(project_name);

                //刪除gitlab project
                gitlabService.deleteRootProject(project_name);
            }

            //將class_question裡的題目刪除
            aDbManager.deleteAssignment(cid, qid, created_time);
        }catch(Exception e){
            return new Result(400, "Delete Assignment Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Delete Assignment Successfull!", "");
    }
}
