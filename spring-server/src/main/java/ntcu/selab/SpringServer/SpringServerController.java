package ntcu.selab.SpringServer;

import ntcu.selab.SpringServer.config.GitlabConfig;
import ntcu.selab.SpringServer.service.GitlabService;
import ntcu.selab.SpringServer.config.JenkinsConfig;
import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.data.User;
import ntcu.selab.SpringServer.db.CourseDBManager;
import ntcu.selab.SpringServer.db.QuestionDBManager;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;
import ntcu.selab.SpringServer.service.JenkinsService;
import ntcu.selab.SpringServer.service.StudentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringServerController {

    @GetMapping("/123")
    public void test() throws Exception{
        User user = UserDBManager.getObject().getUserInfo("acs108102");
        //System.out.println(user.getClasses());
        //User user = new User();
        user.setClasses("2");
        UserDBManager.getObject().updateUser(user);
        //System.out.println(user.getClasses());
        // QuestionDBManager uDbManager = QuestionDBManager.getObject();
        // User user = new User("aa","asd","122ww@gmail.com","qqq","student");
        // user.setId("111");
        // uDbManager.addUser(user);
        //uDbManager.ModifyPassword("asd", "qqq", "aaa");
        // return uDbManager.getQuestionById("a0001");
        
        // Class.forName("com.mysql.cj.jdbc.Driver");  
        // Connection con = DriverManager.getConnection(  
        // "jdbc:mysql://120.108.204.152:3306/spring_server","root","secret");  
        // Statement stmt=con.createStatement();  
        // ResultSet rs=stmt.executeQuery("select * from User");  
        // while(rs.next()){  
        //     System.out.println(rs.getInt(1)+" "+rs.getString(2));  
        // }  
        // QuestionDBManager ss = QuestionDBManager.getObject();
        // List<Question> a = ss.getAllQuestion();
        // for(Question s : a){
        //     System.out.println(s.getId());
        //     System.out.println(s.getDescription());
        //     for(int i=1 ; i<11 ; i++){
        //         System.out.print(s.getInput()[i]);
        //     }
        // }
        // HttpURLConnection conn;
        // URL url = new URL("http://120.108.204.152:3000/api/student/add/001");
        //     conn = (HttpURLConnection) url.openConnection();
        //     conn.setRequestMethod("POST");
        //     conn.setConnectTimeout(5000);
        //     conn.setReadTimeout(5000);
        //     conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
        //     conn.setDoOutput(true);
        //     conn.setDoInput(true);
        //     String info = "student_id=" + "222" + "&" + "student_name=" + "555";
        //     byte[] data = info.getBytes();
        //     conn.connect();
        //     OutputStream out = conn.getOutputStream();
        //     out.write(data);
        //     out.flush();
        //     out.close();
        //     if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
        //         throw new RuntimeException("Failed : HTTP error code : " +
        //         conn.getResponseCode()+" "+conn.getResponseMessage());
        //     }
        //     conn.disconnect();
        
        // return "ok";
    }

    @GetMapping ("/create")
    public String hello() throws Exception {
        /*JenkinsService temp = JenkinsService.getObject();
        temp.createJob("test");
        GitlabService gitlab = GitlabService.getObject();
        GitlabProject project = gitlab.createRootProject("test");
        gitlab.setGitlabIntegrations(project,"test");
        gitlab.addMember(gitlab.getUserByName("a001"),"test");*/
        GitlabService gitlab = GitlabService.getObject();
        GitlabUser user;
        user = gitlab.updateUserName("a001", "a001");
        return user.getName();
    }
    @GetMapping ("/delete")
    public String hello2(){
        GitlabService gitlab = GitlabService.getObject();
        gitlab.deleteRootProject("test");
        return "ok";
    }
    /*@GetMapping ("/config")
    public String hello2() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabApiToken();
    }
    @GetMapping ("/config1")
    public String hello3() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabRootUrl();
    }
    @GetMapping ("/config2")
    public String hello4() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabHostUrl();
    }*/
}
