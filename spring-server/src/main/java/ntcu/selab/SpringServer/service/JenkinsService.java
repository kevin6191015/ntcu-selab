package ntcu.selab.SpringServer.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

import javax.swing.text.AbstractDocument.Content;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.Consts;

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

    public String getCrumb(){
        HttpURLConnection conn = null;
        BufferedReader br;
        String jenkinsCrumb = null;
        try{
            URL url = new URL(jenkinsRootUrl + "/crumbIssuer/api/json");
            conn = (HttpURLConnection) url.openConnection();
            Base64.Encoder encoder = Base64.getEncoder();
            String account  = jenkinsRootUsername + ":" + jenkinsRootPassword;
            String encoding = "Basic " + encoder.encodeToString(account.getBytes());
            conn.setRequestProperty("Authorization",encoding);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
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
    public String getConfig(){
        StringBuilder sb = new StringBuilder();
        String strConfig = null;
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("pipeline_config.xml");
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
            BufferedReader buf = new BufferedReader(reader);) {
        while ((strConfig = buf.readLine()) != null) {
            sb.append(strConfig);
            sb.append("\n");
        }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return sb.toString();
    }
    public void createJob(String jobName){
        HttpURLConnection conn = null;
        try{
            String crumb = getCrumb();
            String urls = jenkinsRootUrl + "/createItem?name=" + jobName;
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            Base64.Encoder encoder = Base64.getEncoder();
            String account  = jenkinsRootUsername + ":" + jenkinsRootPassword;
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            String xml = getConfig();
            byte[] data = xml.getBytes();
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestProperty("Jenkins-Crumb", crumb);
            conn.setRequestProperty("Authorization", "Basic "+ encoder.encodeToString(account.getBytes()));
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            System.out.println(conn.getResponseCode());
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }
}
