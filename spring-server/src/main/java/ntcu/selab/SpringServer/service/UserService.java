package ntcu.selab.SpringServer.service;

import ntcu.selab.SpringServer.config.PasswordConfig;
import ntcu.selab.SpringServer.data.Course;
import ntcu.selab.SpringServer.data.Question;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.data.User;
import ntcu.selab.SpringServer.db.CourseDBManager;
import ntcu.selab.SpringServer.db.QuestionDBManager;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;
import org.gitlab.api.models.GitlabUser;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.csvreader.CsvReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserService {
    private static UserService object = new UserService();
    private UserDBManager dbManager = UserDBManager.getObject();
    private StudentDBManager sDbManager = StudentDBManager.getObject();
    private GitlabService gitlabService = GitlabService.getObject();
    private CourseDBManager cDbManager = CourseDBManager.getObject();
    private QuestionDBManager qDbManager = QuestionDBManager.getObject();
    private PasswordConfig passwordConfig = PasswordConfig.getObject();

    public static UserService getObject() {
        return object;
    }

    @GetMapping("/getUsers")
    public Result getUsers() throws Exception{
        List<User> users = dbManager.getAllUser();
        List<JSONObject> userlist = new ArrayList<>();

        try{
            for (User user : users) {
                JSONObject Object = new JSONObject();
                Object.put("gitlabId", user.getGitlabId());
                Object.put("password", user.getPassword());
                Object.put("role", user.getRole());
                Object.put("gitlabToken", user.getGitlabToken());
                Object.put("name", user.getName());
                Object.put("id", user.getId());
                Object.put("username", user.getUserName());
                Object.put("CLASSES", user.getClasses());
                Object.put("email", user.getEmail());
                userlist.add(Object);
            }
        }catch(Exception e){
            return new Result(400, "Get Users Failed! " + e.getMessage(), "");
        }
        JSONObject root = new JSONObject();
        root.put("Users", userlist);
        return new Result(200, "Get Users Successfull!", root);
    }

    @PostMapping("/addUser")
    public Result createAccount(@RequestBody User user) throws Exception{
        String error = getErrorMessage(user);
        if (error.isEmpty()) {
            try {
                register(user);
                return new Result(200, "Add User Successfull!", "");
            } catch (IOException e) {
                return new Result(400, "Add User failed! " + e.getMessage(), "");
            }
        } else {
            return new Result(400, "Add User failed! " + error, "");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> createAccounts(@RequestParam("file") MultipartFile uploadedInputStream) throws Exception{
        PushbackInputStream test = new PushbackInputStream(uploadedInputStream.getInputStream());
        int ch = test.read();
        if(ch != 0xEF){
            test.unread(ch);
        }else if((ch=test.read()) != 0xBB){
            test.unread(ch);
            test.unread(0xef);
        }else if ((ch=test.read()) != 0xBF){
            throw new IOException("Wrong UTF-8 format");
        }
        HttpHeaders header = new HttpHeaders();
        List<User> users = new ArrayList<>();
        String error = "";
        try {
            CsvReader csvReader = new CsvReader(new InputStreamReader(test, "UTF-8"));
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                User user = new User();
                String id = csvReader.get("id");
                String username = csvReader.get("Username");
                String password = csvReader.get("Password");
                String name = csvReader.get("Name");
                String email = csvReader.get("Email");
                String role = csvReader.get("Role");

                user.setId(id);
                user.setUserName(username);
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
                user.setRole(role);

                error = getErrorMessage(users, user);
                if (error.isEmpty()) {
                    users.add(user);
                } else {
                    System.out.println("Error occured: " + error);
                    break;
                }
            }
            csvReader.close();
            if (error == null || error.isEmpty()) {
                for (User user : users) {
                    register(user);
                }
                return new ResponseEntity<Object>(header, HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(error, header, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("failed", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/updatePassword")
    public Result updatePassword(@RequestParam("username") String username
    , @RequestParam("currentPassword") String currentPassword
    , @RequestParam("newPassword") String newPassword) throws Exception{

        boolean check = dbManager.checkPassword(username, currentPassword);
        if (check) {
            String gitlabId = dbManager.getGitlabidByUsername(username);
            gitlabService.updatePassword(gitlabId, newPassword);
            dbManager.ModifyPassword(username, currentPassword, newPassword);
            return new Result(200, "Update Password Successfull!", "");
        } else {
            return new Result(400, "Update Password Failed!", "");
        }
    }

    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User newUser) {
        try{

            User user = dbManager.getUserInfo(newUser.getId());
            //將user的資料同步到gitlab
            if(!user.getRole().equals("ROOT")){
                gitlabService.updateEmail(user.getEmail(), user.getName());
                gitlabService.updateName(newUser.getName(), user.getName());
                gitlabService.updatePassword(newUser.getPassword(), user.getName());
                gitlabService.updateUserName(newUser.getUserName(), user.getUserName());
            }

            //將user的資料同步到student(如果是學生)
            if(user.getRole().equals("student") && !user.getClasses().equals("")){
                Student student = new Student(newUser.getId(), newUser.getName());
                String[] split = user.getClasses().split(",");
                for (int i=0; i<split.length; i++){
                    sDbManager.updateStudent(split[i], student);
                }       
            }else{
                //將user的資料同步到course
                List<Course> courses = cDbManager.getAllCourses();
                for(Course course : courses){
                    //如果是老師
                    if(user.getRole().equals("teacher")){
                        //更新課程資訊
                        if(course.getTeacher() == user.getName()){
                            course.setTeacher(newUser.getName());
                            cDbManager.updateCourse(course.getId(), course);
                        }
    
                        //更新question裡老師相關資料(question bank2)
                        List<Question> questions = qDbManager.getQuestionsByTeacher(user.getName());
                        for(Question question : questions){                
                            question.setTeacher(newUser.getName());
                            qDbManager.updateQuestion(question.getId(), question);
                        }
                    }
                    //如果是助教
                    else{
                        //更新助教資料
                        if(course.getTA() == user.getName()){
                            course.setTA(newUser.getName());
                            cDbManager.updateCourse(course.getId(), course);
                        }
                    }        
                }               
            }
            
            //更新user資料(user database)
            user.setName(newUser.getName());
            user.setPassword(passwordConfig.encrypt(newUser.getPassword()));            
            //user.setRole(role);
            user.setEmail(newUser.getEmail());
            dbManager.updateUser(user);

        }catch(Exception e){
            return new Result(400, "Update User Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Update User Successfull!", ""); 
    }

    @GetMapping("deleteUser")
    public Result deleteUser(@RequestParam String uid){
        try{                     
            //刪除user所在class裡的資料(student databae)
            String classes = dbManager.getClassesByName(dbManager.getUsernameById(uid));
            if(!classes.equals("")){
                String[] split = classes.split(",");
                for (int i=0; i<split.length; i++){
                    sDbManager.deleteStudent(split[i], uid);;
                } 
            }

            //刪除user資料(user database)
            User user = dbManager.getUserInfo(uid); 
            unregister(user);
        }catch(Exception e){
            return new Result(400, "Delete User Failed! " + e.getMessage(), "");
        }
        return new Result(200, "Delete User Successfull!", "");  
    }

    private String getErrorMessage(List<User> users, User user) throws Exception{
        
        String errorMessage = getErrorMessage(user);

        if (errorMessage.isEmpty()) {
            if (isDuplicateUsername(users, user.getUserName())) {
                errorMessage = "username : " + user.getUserName() + " is duplicated in student list.";
            } else if (isDuplicateEmail(users, user.getEmail())) {
                errorMessage = "Email : " + user.getEmail() + " already exists.";
            }
        }
        return errorMessage;
    }

    private String getErrorMessage(User user) throws Exception{
        String errorMessage = "";
        if (isPasswordTooShort(user.getPassword())) {
            errorMessage = user.getName() + " : Password must be at least 8 characters.";
        } else if (isDuplicateUsername(user.getUserName())) {
            errorMessage = "username : " + user.getUserName() + " already exists.";
        } else if (user.getRole() == null) {
            errorMessage = "Role is empty";
        } else if (isDuplicateEmail(user.getEmail())) {
            errorMessage = "Email : " + user.getEmail() + " already exists.";
        }
        return errorMessage;
    }

    private boolean isDuplicateUsername(String username) throws Exception{
        boolean isDuplicateUsername = false;
        if (dbManager.checkUsername(username)) {
            isDuplicateUsername = true;
        }
        return isDuplicateUsername;
    }

    private boolean isDuplicateUsername(List<User> users, String username) {
        boolean isDuplicateUsername = false;
        for (User user : users) {
            if (username.equals(user.getUserName())) {
                isDuplicateUsername = true;
                break;
            }
        }
        return isDuplicateUsername;
    }

    private boolean isPasswordTooShort(String password) {
        boolean isPasswordTooShort = false;
        if (password.length() < 8) {
            isPasswordTooShort = true;
        }
        return isPasswordTooShort;
    }

    private boolean isDuplicateEmail(String email) throws Exception{
        boolean isDuplicateEmail = false;
        if (dbManager.checkEmail(email)) {
            isDuplicateEmail = true;
        }
        return isDuplicateEmail;
    }

    private boolean isDuplicateEmail(List<User> users, String email) {
        boolean isDuplicateEmail = false;
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                isDuplicateEmail = true;
                break;
            }
        }
        return isDuplicateEmail;
    }

    private void register(User user) throws Exception {
        if(user.getRole().equals("student")){
            GitlabUser gitlabUser = gitlabService.createUser(
                user.getEmail(), user.getPassword(), user.getUserName(), user.getName());
            user.setGitlabToken(gitlabUser.getPrivateToken());
            user.setGitlabId(String.valueOf(gitlabUser.getId()));
        }

        user.setPassword(passwordConfig.encrypt(user.getPassword()));
        dbManager.addUser(user);     
    }

    private void unregister(User user) throws Exception { 
        if(user.getRole().equals("student") )    
            gitlabService.deleteUserByID(Integer.valueOf(user.getGitlabId()));

        dbManager.DeleteUserById(user.getId());
    }
}
