package ntcu.selab.SpringServer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import ntcu.selab.SpringServer.config.JenkinsConfig;

public class JenkinsService {
    private static final Logger logger = LoggerFactory.getLogger(JenkinsService.class);
    private static JenkinsService object = new JenkinsService();
    private JenkinsConfig jenkinsConfig;
    private String jenkinsHostUrl;
    private String jenkinsRootUsername;
    private String jenkinsRootPassword;
    private String jenkinsApiToken;
    private String jenkinsRootUrl;
    private final String contentType = "";
    private final String jenkinsCrumb = "";

    public JenkinsService(){
        try {
            jenkinsConfig = JenkinsConfig.getObject();
            jenkinsRootUrl = jenkinsConfig.getJenkinsRootUrl();
            jenkinsRootUsername = jenkinsConfig.getJenkinsRootUsername();
            jenkinsRootPassword = jenkinsConfig.getJenkinsRootPassword();
            jenkinsApiToken = jenkinsConfig.getJenkinsApiToken();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public static JenkinsService getObject(){
        return object;
    }

    public String getCrumb(String username , String password){
        HttpURLConnection conn = null;
        BufferedReader br;
        String jenkinsCrumb = null;
        try{
            URL url = new URL(jenkinsRootUrl + "/crumbIssuser/api/json");
            conn = (HttpURLConnection) url.openConnection();
            Base64.Encoder encoder = Base64.getEncoder();
            String account  = username + ":" + password;
            conn.setRequestProperty("Authorization", "Basic "+ encoder.encodeToString(account.getBytes()));
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            try{
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = br.readLine();
                JSONObject obj = new JSONObject(line);
                jenkinsCrumb = obj.getString("crumb");
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally{
            if(conn != null)
                conn.disconnect();
        }
        return jenkinsCrumb;
    }

    
}
