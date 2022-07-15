package ntcu.selab.SpringServer.data;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String Id;
    private String GitlabId;
    private String Name;
    private String UserName;
    private String Password;
    private String GitlabToken;
    private String Role;
    private String email;
    private List<String> classes = new ArrayList<>();

    public User(String name, String username, String email, String password, String role) {
        this.Name = name;
        this.UserName = username;
        this.Password = password;
        this.Role = role;
        this.email = email;
    }

    public User() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getGitlabId() {
        return GitlabId;
    }

    public void setGitlabId(String gitlabid) {
        this.GitlabId = gitlabid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String username) {
        this.UserName = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        if (password == null || password == "") {
            this.Password = "1234567";
        } else {
            this.Password = password;
        }
    }

    public String getGitlabToken() {
        return GitlabToken;
    }

    public void setGitlabToken(String gitlabtoken) {
        this.GitlabToken = gitlabtoken;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClasses(){
        String combine = "";
        //System.out.println(classes.size());
        for(int i=1 ; i<this.classes.size() ; i++){
            //System.out.println("i= " + i);
            if(i == 1){
                combine = this.classes.get(i);
                
            }else{
                combine = combine + "," + this.classes.get(i);
            }
            
        }
        return combine;
    }

    public void setClasses(String c){
        //System.out.println(c);
        String[] split = c.split(",");
        for (int i=1; i<split.length; i++){
            System.out.println("s[i]= " + split[i]);
            this.classes.add(split[i]); 
        }
    }

    public void addClasses(String cid){
        this.classes.add(cid);
    }

    public void deleteClasses(String cid){
        this.classes.remove(new String(cid));
    }

    public void getList(){
        System.out.println(classes.size());
        System.out.println(classes.get(0));
        System.out.println(classes.get(1));
        // for(int i=1 ; i<classes.size() ; i++){
        //     System.out.println(classes.get(i));
        // }
    }
}
