package ntcu.selab.SpringServer.service;

import ntcu.selab.SpringServer.data.Student;
import ntcu.selab.SpringServer.data.User;
import ntcu.selab.SpringServer.db.StudentDBManager;
import ntcu.selab.SpringServer.db.UserDBManager;
import org.gitlab.api.models.GitlabUser;

import net.minidev.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.csvreader.CsvReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserService {
    private static UserService object = new UserService();
    private UserDBManager dbManager = UserDBManager.getObject();
    private StudentDBManager sDbManager = StudentDBManager.getObject();
    private GitlabService gitlabService = GitlabService.getObject();

    public static UserService getObject() {
        return object;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<Object> getUsers() throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        List<User> users = dbManager.getAllUser();
        List<JSONObject> userlist = new ArrayList<>();

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
            userlist.add(Object);
        }

        JSONObject root = new JSONObject();
        root.put("Users", userlist);

        return new ResponseEntity<Object>(root, header, HttpStatus.OK);
    }

    @GetMapping("/addUser")
    public ResponseEntity<Object> createAccount(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role) throws Exception{
        HttpHeaders header = new HttpHeaders();
        User user = new User(name, username, email, password, role);
        user.setId(id);
        String error = getErrorMessage(user);
        if (error.isEmpty()) {
            try {
                register(user);
                return new ResponseEntity<Object>(header, HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<Object>("Failed", header, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<Object>(error, header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> createAccounts(@RequestParam("file") MultipartFile uploadedInputStream) throws Exception{
        HttpHeaders header = new HttpHeaders();
        List<User> users = new ArrayList<>();
        String error = "";
        try {
            CsvReader csvReader = new CsvReader(new InputStreamReader(uploadedInputStream.getInputStream(), "BIG5"));
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
    public ResponseEntity<Object> updatePassword(@RequestParam("username") String username,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword) throws Exception{
        HttpHeaders header = new HttpHeaders();
        boolean check = dbManager.checkPassword(username, currentPassword);
        if (check) {
            String gitlabId = dbManager.getGitlabidByUsername(username);
            gitlabService.updatePassword(gitlabId, newPassword);
            dbManager.ModifyPassword(username, currentPassword, newPassword);
            return new ResponseEntity<>(header, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Your current password is wrong", header, HttpStatus.OK);
        }
    }

    @GetMapping(value = "getUserCsvFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> getUserCsvFile() throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment;filename=" + "StudentTemplate.csv");
        InputStream targetStream = this.getClass().getResourceAsStream("/StudentTemplate.csv");

        byte[] file = IOUtils.toByteArray(targetStream);
        return new ResponseEntity<Object>(file, header, HttpStatus.OK);

    }

    @GetMapping("updateUser")
    public ResponseEntity<Object> updateUser(@RequestParam String uid, @RequestParam String username
    , @RequestParam String name, @RequestParam String password, @RequestParam String role
    , @RequestParam String email) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

        try{
            User user = dbManager.getUserInfo(uid);
            //將user的資料同步到gitlab
            //gitlabService.updateEmail(user.getEmail(), user.getName());
            gitlabService.updateName(name, user.getName());
            gitlabService.updatePassword(password, user.getName());
            gitlabService.updateUserName(username, user.getUserName());

            //更新user資料(user database)
            user.setName(name);
            user.setPassword(password);            
            user.setRole(role);
            user.setEmail(email);
            dbManager.updateUser(user);

            //將user的資料同步到student
            Student student = new Student(uid, username);
            String[] split = user.getClasses().split(",");
            for (int i=0; i<split.length; i++){
                sDbManager.updateStudent(split[i], student);
            }       
        }catch(Exception e){
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK); 
    }

    @GetMapping("deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam String uid){
        HttpHeaders header = new HttpHeaders();
        header.add("Content_Type", "application/json");

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
            unregister(uid, user.getGitlabId());
        }catch(Exception e){
            return new ResponseEntity<>("Failed!", header, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(header, HttpStatus.OK); 
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
        GitlabUser gitlabUser = gitlabService.createUser(
                user.getEmail(), user.getPassword(), user.getUserName(), user.getName());
        user.setGitlabToken(gitlabUser.getPrivateToken());
        user.setGitlabId(String.valueOf(gitlabUser.getId()));

        dbManager.addUser(user);
    }

    private void unregister(String uid, String gid) throws Exception {       
        gitlabService.deleteUserByID(Integer.valueOf(gid));

        dbManager.DeleteUserById(uid);
    }
}
