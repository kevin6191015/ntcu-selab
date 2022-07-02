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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

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
            // HttpPost post = new HttpPost(urls);

            // post.addHeader(contentType, "application/xml");
            // post.addHeader(jenkinsCrumb, crumb);

            // String config = getConfig();
            // StringEntity se = new StringEntity(config, ContentType.create("text/xml", Consts.UTF_8));
            // se.setChunked(true);
            // post.setEntity(se);

            // HttpClient client = new DefaultHttpClient();
            // client.execute(post);
            
            // URL url = new URL(urls);
            // conn = (HttpURLConnection) url.openConnection();
            // Base64.Encoder encoder = Base64.getEncoder();
            // String account  = jenkinsRootUsername + ":" + jenkinsRootPassword;
            // String encoding = encoder.encodeToString(account.getBytes("UTF-8"));
            // conn.setRequestMethod("POST");
            // conn.setDoOutput(true);
            // conn.setDoInput(true);
            // conn.setRequestProperty("Connection", "Keep-Alive");
            // conn.setRequestProperty("Charset", "UTF-8");
            // String xml = getConfig();
            // byte[] data = xml.getBytes();
            // conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // conn.setRequestProperty("Content-Type", "text/xml");
            // conn.setRequestProperty("Jenkins-Crumb", crumb);
            // conn.setRequestProperty("Authorization", "Basic "+ encoding);
            // conn.setRequestProperty("User-Agent", "PostmanRuntime/7.29.2");
            // conn.setRequestProperty("Cookie", "JSESSIONID.60fa66d0=node0b6j85h65mm7nkemyjtvtrrmk1697.node0; Path=/; HttpOnly");
            // conn.connect();
            // OutputStream out = conn.getOutputStream();
            // out.write(data);
            // out.flush();
            // out.close();
            // if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            //     throw new RuntimeException("Failed : HTTP error code : " +
            //     conn.getResponseCode()+" "+conn.getResponseMessage());
            // }
            // BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            // System.out.println("Output from Server .... \n");
            // String output;
            // while (( output = br.readLine()) != null) {
            //     System.out.println(output);
            // }
            // conn.disconnect();

            String cmd = "curl --location --request POST 'http://120.108.204.152:8080/createItem?name=" + jobName + "' ";
            cmd += "--header 'Content-Type: text/xml' --header 'Jenkins-Crumb: " + getCrumb() + "' ";
            cmd += "--header 'Authorization: Basic cm9vdDoxMjM0NTY3OA==' --header 'Cookie: JSESSIONID.60fa66d0=node0y0ckg0m0mit91jsh9142kz1gh1851.node0' --data-raw '";
            cmd += getConfig();
            cmd += "' ";
            System.out.println(cmd);
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null) {
                System.out.println(content);
                content = br.readLine();
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public void deleteJob(String jobName){
        try {
            String crumb = getCrumb();
            HttpClient client = new DefaultHttpClient();
            String url = jenkinsRootUrl + "/job/" + jobName + "/doDelete";
            HttpPost post = new HttpPost(url);
      
            post.addHeader(contentType, "application/x-www-form-urlencoded");
            post.addHeader(jenkinsCrumb, "48048c2f646bb31c4fa85f673da6bfe79e4e190bdf08a443774eacc7363f288d");
      
            client.execute(post);
          } catch (Exception e) {
            logger.error(e.getMessage());
          }
    }

    public void buildJob(String jobName) {
        try {
          String crumb = getCrumb();
    
          String url = jenkinsRootUrl + "/job/" + jobName + "/build";
          HttpPost post = new HttpPost(url);
    
          post.addHeader(contentType, "application/xml");
          post.addHeader(crumb, "48048c2f646bb31c4fa85f673da6bfe79e4e190bdf08a443774eacc7363f288d");
    
          List<NameValuePair> params = new ArrayList<>();
          params.add((NameValuePair) new BasicNameValuePair("token", jenkinsApiToken));
    
          UrlEncodedFormEntity ent = null;
          ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
          post.setEntity(ent);
    
          HttpClient client = new DefaultHttpClient();
          client.execute(post);
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
          String input = jenkinsRootUsername + ":" + jenkinsRootPassword;
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
