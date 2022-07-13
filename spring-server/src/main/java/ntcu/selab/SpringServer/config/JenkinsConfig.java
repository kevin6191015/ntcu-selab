package ntcu.selab.SpringServer.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsConfig {
    private static final String PROPERTY_FILE = "/jenkins.properties";
    private Properties props;
    private static JenkinsConfig object = new JenkinsConfig();
    private static final Logger logger = LoggerFactory.getLogger(JenkinsConfig.class);

    public JenkinsConfig(){
        InputStream is = this.getClass().getResourceAsStream(PROPERTY_FILE);
        try{
            props = new Properties();
            props.load(is);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    public static JenkinsConfig getObject(){
        return  object;
    }

    public String getJenkinsHostUrl() throws Exception{
        String jenkinsurl = System.getenv("JENKINS_HOST");
        if (jenkinsurl != null && !jenkinsurl.equals("")){
            return jenkinsurl;
        }
        if(props != null){
            return props.getProperty("JENKINS_HOST").trim();
        }
        throw new Exception("Could not found JENKINS_HOST_URL");
    }

    public String getJenkinsRootUsername() throws Exception{
        String jenkinsRootUsername = System.getenv("JEMKINS_ROOT_USERNAME");
        if (jenkinsRootUsername != null && !jenkinsRootUsername.equals("")){
            return jenkinsRootUsername;
        }
        else if (props != null){
            return props.getProperty("JENKINS_ROOT_USERNAME").trim();
        }
        throw new Exception("Could not found JENKINS_ROOT_USERNAME");
    }

    public String getJenkinsRootPassword() throws Exception{
        String jenkinsRootPassword = System.getenv("JENKINS_ROOT_PASSWORD");
        if (jenkinsRootPassword != null && !jenkinsRootPassword.equals("")){
            return jenkinsRootPassword;
        }
        if (props != null){
            return props.getProperty("JENKINS_ROOT_PASSWORD").trim();
        }
        throw new Exception("Could not found JENKINS_ROOT_PASSWORD");
    }

    public String getJenkinsApiToken() throws Exception{
        String jenkinsApiToken = System.getenv("JENKINS_API_TOKEN");
        if (jenkinsApiToken != null && !jenkinsApiToken.equals("")){
            return jenkinsApiToken;
        }
        if (props != null){
            return props.getProperty("JENKINS_API_TOKEN").trim();
        }
        throw new Exception("Could not found JENKINS_API_TOKEN");
    }

    public String getJenkinsRootUrl() throws Exception{
        String jenkinsHostUrl = getJenkinsHostUrl();
        if (jenkinsHostUrl != null && !jenkinsHostUrl.equals("")){
            return jenkinsHostUrl.substring(0,jenkinsHostUrl.indexOf("//")+2)
                + getJenkinsRootUsername()
                + ":"
                + getJenkinsApiToken()
                + "@"
                + jenkinsHostUrl.substring(jenkinsHostUrl.indexOf("//") + 2,jenkinsHostUrl.length());
        }
        if (props != null){
            return props.getProperty("JENKINS_ROOT_URL");
        }
        throw new Exception("Could not found JENKINS_ROOT_URL");
    }
}
