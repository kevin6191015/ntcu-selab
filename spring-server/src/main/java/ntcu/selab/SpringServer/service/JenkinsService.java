package ntcu.selab.SpringServer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ntcu.selab.SpringServer.config.JenkinsConfig;

public class JenkinsService {
    private static final Logger logger = LoggerFactory.getLogger(JenkinsService.class);
    private static JenkinsService object = new JenkinsService();
    private JenkinsConfig jenkinsConfig;
    private String jenkinsRootUsername;
    private String jenkinsApiToken;
    private String jenkinsRootUrl;

    public JenkinsService(){
        try {
            jenkinsConfig = JenkinsConfig.getObject();
            jenkinsRootUrl = jenkinsConfig.getJenkinsRootUrl();
            jenkinsRootUsername = jenkinsConfig.getJenkinsRootUsername();
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
            String account  = jenkinsRootUsername + ":" + jenkinsApiToken;
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
            String account  = jenkinsRootUsername + ":" + jenkinsApiToken;
            String encoding = encoder.encodeToString(account.getBytes("UTF-8"));
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            String xml = getConfig();
            byte[] data = xml.getBytes();
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestProperty("Jenkins-Crumb", crumb);
            conn.setRequestProperty("Authorization", "Basic "+ encoding);
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

        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public void deleteJob(String jobName){
        try {
            String crumb = getCrumb();
            HttpURLConnection conn = null;
            String urls = jenkinsRootUrl + "/job/" + jobName + "/doDelete";   
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            Base64.Encoder encoder = Base64.getEncoder();
            String account  = jenkinsRootUsername + ":" + jenkinsApiToken;
            String encoding = encoder.encodeToString(account.getBytes("UTF-8"));
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Jenkins-Crumb", crumb);
            conn.setRequestProperty("Authorization", "Basic "+ encoding);
            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            conn.disconnect();
          } catch (Exception e) {
            logger.error(e.getMessage());
          }
    }

    public void buildJob(String jobName) {
        try {
            String crumb = getCrumb();
            HttpURLConnection conn = null;
            String urls = jenkinsRootUrl + "/job/" + jobName + "/build";
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            Base64.Encoder encoder = Base64.getEncoder();
            String account  = jenkinsRootUsername + ":" + jenkinsApiToken;
            String encoding = encoder.encodeToString(account.getBytes("UTF-8"));
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Jenkins-Crumb", crumb);
            conn.setRequestProperty("Authorization", "Basic "+ encoding);
            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " +
                conn.getResponseCode()+" "+conn.getResponseMessage());
            }
            conn.disconnect();
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
    }

    public String getCommitMessage(String jobName, int num) {
        String console = getCompleteConsole(jobName, num);
        String beginStr = "Commit message: ";
        String endStr = "\n";
        int beginIndex = console.indexOf(beginStr) + beginStr.length();
        int endIndex = console.indexOf(endStr, beginIndex);
        // extract commit message
        String message = console.substring(beginIndex, endIndex);
        return message;
    }

    public String getCompleteConsole(String jobName, int num) {
        String consoleUrl = getConsoleUrl(jobName, num);
        String console = "";
        HttpURLConnection conn = null;
        try {
          if (Thread.interrupted()) {
            throw new InterruptedException();
          }
          URL url = new URL(consoleUrl);
          conn = (HttpURLConnection) url.openConnection();
          String input = jenkinsRootUsername + ":" + jenkinsApiToken;
          Base64.Encoder encoder = Base64.getEncoder();
          String encoding = "Basic " + encoder.encodeToString(input.getBytes());
          conn.setRequestProperty("Authorization", encoding);
          conn.setReadTimeout(10000);
          conn.setConnectTimeout(15000);
          conn.setRequestMethod("GET");
          conn.connect();
          if (Thread.interrupted()) {
            throw new InterruptedException();
          }
          try (BufferedReader br = new BufferedReader(
              new InputStreamReader(conn.getInputStream(), "UTF-8"));) {
            String str = "";
            StringBuilder sb = new StringBuilder();
            while (null != (str = br.readLine())) {
              sb.append("\n");
              sb.append(str);
            }
            console = sb.toString();
          }
        } catch (Exception e) {
          logger.error(e.getMessage());
        } finally {
          if (conn != null) {
            conn.disconnect();
          }
        }
        return console;
      }

    public String getConsoleUrl(String jobName, int num) {
        return (jenkinsRootUrl + "/job/" + jobName + "/" + num + "/consoleText");
    }

    public String getJobName(String username, String projectName) {
        return username + "_" + projectName;    
    }
}
