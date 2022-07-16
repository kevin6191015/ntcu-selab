package ntcu.selab.SpringServer.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;

import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.data.User;

public class UserDBManager {
    private static UserDBManager dbManager = null;
    private static final Logger logger = LoggerFactory.getLogger(UserDBManager.class);
    private static MysqlDatabase database = MysqlDatabase.getObject();
    private static HttpURLConnection conn = null;
    private static StringBuilder response = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static JSONArray jsonarray = null;
    private static JSONObject jsonobject = null;

    public static UserDBManager getObject() {
        if (dbManager == null) {
            dbManager = new UserDBManager();
        }
        return dbManager;
    }

    public void addUser(User user) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/add");
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "ID=" + user.getId() + "&GITLAB_ID=" + user.getGitlabId()
            + "&USERNAME=" + user.getUserName() + "&NAME=" + user.getName() + "&PASSWORD=" + 
            user.getPassword() + "&GITLAB_TOKEN=" + user.getGitlabToken() + "&ROLE=" + user.getRole()
            + "&EMAIL=" + user.getEmail() + "&CLASSES=" + user.getClasses();
            byte[] data = info.getBytes();
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            conn.disconnect();
            jsonobject = new JSONObject(response.toString()); 
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
    }

    public String getPassword(String username) throws Exception{
        String password = "";
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/pw/" +username);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        password = jsonobject.getString("PASSWORD");
        return password;
    }

    public void ModifyPassword(String username, String password, String newpassword) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/update/pw/" + username + "/" + newpassword);
        if(checkPassword(username, password)){
            conn = database.getConnection(url, "POST");
        }else{
            System.out.println("Wrong password!");
        }  
    }

    public boolean checkPassword(String username, String password)throws Exception {
        boolean check = false;
        String current = getPassword(username);
        if (current.equals(password)) {
            check = true;
        }
        return check;
    }

    public String getUseridByUsername(String username) throws Exception{
        String id = "";
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/id/" + username);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        id = jsonobject.getString("ID");
        return id;
    }

    public String getGitlabidByUsername(String username) throws Exception{
        String gitlabid = "";
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "gitlab_id/" + username);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        gitlabid = jsonobject.getString("GITLAB_ID");
        return gitlabid;
    }

    public User getUserInfo(String uid) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/userbyid/" + uid);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        User user = new User();
        user.setId(uid);
        user.setGitlabId(jsonobject.getString("GITLAB_ID"));
        user.setUserName(jsonobject.getString("USERNAME"));
        user.setEmail(jsonobject.getString("EMAIL"));
        user.setName(jsonobject.getString("NAME"));
        user.setGitlabToken(jsonobject.getString("GITLAB_TOKEN"));
        user.setPassword(jsonobject.getString("PASSWORD"));
        user.setRole(jsonobject.getString("ROLE"));
        user.setClasses(jsonobject.getString("CLASSES"));              
        return user;
    }

    public List<User> getAllUser() throws Exception{      
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/user");       
        List<User> users = new ArrayList<>();

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        for(int i=0 ; i<jsonarray.length() ; i++){
            jsonobject = jsonarray.getJSONObject(i);
            User user = new User();
            user.setId(jsonobject.getString("ID"));
            user.setGitlabId(jsonobject.getString("GITLAB_ID"));
            user.setUserName(jsonobject.getString("USERNAME"));
            user.setEmail(jsonobject.getString("EMAIL"));
            user.setName(jsonobject.getString("NAME"));
            user.setGitlabToken(jsonobject.getString("GITLAB_TOKEN"));
            user.setPassword(jsonobject.getString("PASSWORD"));
            user.setRole(jsonobject.getString("ROLE"));
            users.add(user); 
        }       
        return users;
    }

    public void addClasses(User user, String cid){
        user.addClasses(cid);
        String classes = user.getClasses();        
        user.setClasses(classes);
    }

    public void deleteClasses(User user, String cid){
        user.deleteClasses(cid);
        String classes = user.getClasses();
        user.setClasses(classes);   
    }

    public boolean checkUsername(String username) throws Exception{
        boolean check = false;
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/checkbyusername/" + username);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        int count = jsonobject.getInt("count(*)");
        if(count >= 1){
            check = true;
        }
        return check;
    }

    public boolean checkEmail(String email) throws Exception{
        boolean check = false;
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/checkbyemail/" + email);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        int count = jsonobject.getInt("count(*)");
        if(count >= 1){
            check = true;
        }
        return check;
    }

    public boolean checkUserId(String uid) throws Exception{
        boolean check = false;
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/checkbyid/" + uid);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        int count = jsonobject.getInt("count(*)");
        if(count >= 1){
            check = true;
        }
        return check;
    }

    public String getUsernameById(String uid) throws Exception{
        String username = "";
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/username/" + uid);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        username = jsonobject.getString("USERNAME");
        return username;
    }

    public String getNameById(String uid) throws Exception{
        String name = "";
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/name/" + uid);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        name = jsonobject.getString("NAME");
        return name;
    }

    public String getClassesByName(String name) throws Exception{
        String classes = "";
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/classes/" + name);       

        conn = database.getConnection(url, "GET");
        response = new StringBuilder();  
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while((line = br.readLine())!= null)
            response.append(line);
        br.close();
        conn.disconnect();
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        classes = jsonobject.getString("CLASSES");
        return classes;
    }

    public void DeleteUserById(String uid) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/delete/" + uid);  
        try{
            conn = database.getConnection(url, "POST");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }            
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            conn.disconnect();
            jsonobject = new JSONObject(response.toString());           
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
        
        
    }

    public void updateUser(User user) throws Exception{
        String dbUrl = MysqlConfig.getObject().getDBUrl();
        URL url = new URL(dbUrl + "user/update/user/" + user.getUserName());
        try{
            conn = database.getConnection(url, "POST");
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "ID=" + user.getId() + "&USERNAME=" + user.getUserName() + "&NAME=" + user.getName() + "&PASSWORD=" + 
            user.getPassword() + "&ROLE=" + user.getRole() + "&EMAIL=" + user.getEmail() + "&CLASSES=" + user.getClasses();
            byte[] data = info.getBytes();
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
            conn.disconnect();
            jsonobject = new JSONObject(response.toString()); 
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }       
    }
}
