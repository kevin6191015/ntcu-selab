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

import ntcu.selab.SpringServer.data.User;

public class UserDBManager {
    private static UserDBManager dbManager = null;
    private static final Logger logger = LoggerFactory.getLogger(UserDBManager.class);
    private static final String dbUrl = "http://120.108.204.152:3000/api/user/";

    public static UserDBManager getObject() {
        if (dbManager == null) {
            dbManager = new UserDBManager();
        }
        return dbManager;
    }

    public void addUser(User user) throws Exception{
        HttpURLConnection conn;
        URL url = new URL(dbUrl + "/add/");
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            String info = "ID=" + user.getId() + "&GITLAB_ID=" + user.getGitlabId()
            + "&USERNAME=" + user.getUserName() + "&NAME=" + user.getName() + "&PASSWORD=" + 
            user.getPassword() + "&GITLAB_TOKEN=" + user.getGitlabToken() + "&ROLE=" + user.getRole()
            + "&EMAIL=" + user.getEmail();
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
            conn.disconnect();
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
    }

    public String getPassword(String username) throws Exception{
        String password = "";
        URL url = new URL(dbUrl + "/pw/" +username);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        password = jsonobject.getString("PASSWORD");
        return password;
    }

    public void ModifyPassword(String username, String password, String newpassword) throws Exception{
        URL url = new URL(dbUrl + "/update/pw/" + username + "/" + newpassword);
        if(checkPassword(username, password)){
            setConnect(url, "POST");
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
        URL url = new URL(dbUrl + "id/" + username);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        id = jsonobject.getString("ID");
        return id;
    }

    public String getGitlabidByUsername(String username) throws Exception{
        String gitlabid = "";
        URL url = new URL(dbUrl + "gitlab_id/" + username);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        gitlabid = jsonobject.getString("GITLAB_ID");
        return gitlabid;
    }

    public User getUserInfo(String id) throws Exception{
        URL url = new URL(dbUrl + "userbyid/" + id);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        User user = new User();
        user.setId(id);
        user.setGitlabId(jsonobject.getString("GITLAB_ID"));
        user.setUserName(jsonobject.getString("USERNAME"));
        user.setEmail(jsonobject.getString("EMAIL"));
        user.setName(jsonobject.getString("NAME"));
        user.setGitlabToken(jsonobject.getString("GITLAB_TOKEN"));
        user.setPassword(jsonobject.getString("PASSWORD"));
        user.setRole(jsonobject.getString("ROLE"));              
        return user;
    }

    public List<User> getAllUser() throws Exception{
        List<User> users = new ArrayList<>();
        URL url = new URL(dbUrl + "user");       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
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

    public boolean checkUsername(String username) throws Exception{
        boolean check = false;
        URL url = new URL(dbUrl + "checkbyusername/" + username);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
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
        URL url = new URL(dbUrl + "checkbyemail/" + email);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        int count = jsonobject.getInt("count(*)");
        if(count >= 1){
            check = true;
        }
        return check;
    }

    public String getUsernameById(String id) throws Exception{
        String username = "";
        URL url = new URL(dbUrl + "username/" + id);       
        JSONArray jsonarray = null;
        JSONObject jsonobject = null;

        StringBuilder response = setConnect(url, "GET");
        jsonarray = new JSONArray( response.toString());
        jsonobject = jsonarray.getJSONObject(0);
        username = jsonobject.getString("USERNAME");
        return username;
    }

    public void DeleteUserById(String id) throws Exception{
       URL url = new URL(dbUrl + "delete/" + id);  
       setConnect(url, "POST");
    }

    public StringBuilder setConnect(URL url, String httpmethod) throws Exception{
        HttpURLConnection conn;
        StringBuilder response = null;
        BufferedReader br = null;
        String line = "";
        try{
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpmethod);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            //int status = conn.getResponseCode();
            response = new StringBuilder();  
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine())!= null)
                response.append(line);
            br.close();
        }catch(HttpStatusCodeException e){
            logger.error(e.getMessage());
        }
        return response;
    }
}
