package ntcu.selab.SpringServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SonarConfig {
    private static final String PROPERTY_FILE = "/sonarqube.properties";
    private static Properties props;
    private static  SonarConfig object = new SonarConfig();
    private static final Logger logger = LoggerFactory.getLogger(JenkinsConfig.class);
    public SonarConfig(){
        InputStream is = this.getClass().getResourceAsStream(PROPERTY_FILE);
        try{
            props = new Properties();
            props.load(is);
        }catch(IOException e){
            logger.error(e.getMessage());
        }
    }
    public static SonarConfig getObject(){
        return  object;
    }

    public static String getSonarHostUrl() throws Exception{
        String SonarUrl = System.getenv("SONAR_HOST_URL");
        if (SonarUrl != null && !SonarUrl.equals("")){
            return SonarUrl;
        }
        if(props != null){
            return props.getProperty("SONAR_HOST_URL").trim();
        }
        throw new Exception("Could not found SONAR_HOST_URL");
    }

    public String getSonarRootUsername() throws Exception{
        String sonarRootUsername = System.getenv("SONAR_ROOT_USERNAME");
        if (sonarRootUsername != null && !sonarRootUsername.equals("")){
            return sonarRootUsername;
        }
        else if (props != null){
            return props.getProperty("SONAR_ROOT_USERNAME").trim();
        }
        throw new Exception("Could not found SONAR_ROOT_USERNAME");
    }

    public String getSonarRootPassword() throws Exception{
        String SonarRootPassword = System.getenv("SONAR_ROOT_PASSWORD");
        if (SonarRootPassword != null && !SonarRootPassword.equals("")){
            return SonarRootPassword;
        }
        if (props != null){
            return props.getProperty("SONAR_ROOT_PASSWORD").trim();
        }
        throw new Exception("Could not found SONAR_ROOT_PASSWORD");
    }

    public String getSonarApiToken() throws Exception{
        String sonarApiToken = System.getenv("SONAR_API_TOKEN");
        if (sonarApiToken != null && !sonarApiToken.equals("")){
            return sonarApiToken;
        }
        if (props != null){
            return props.getProperty("SONAR_API_TOKEN").trim();
        }
        throw new Exception("Could not found SONAR_API_TOKEN");
    }

    public String getSonarRootUrl() throws Exception{
        String sonarHostUrl = getSonarHostUrl();
        if (sonarHostUrl != null && !sonarHostUrl.equals("")){
            return sonarHostUrl.substring(0,sonarHostUrl.indexOf("//")+2)
                    + getSonarRootUsername()
                    + ":"
                    + getSonarApiToken()
                    + "@"
                    + sonarHostUrl.substring(sonarHostUrl.indexOf("//") + 2,sonarHostUrl.length());
        }
        if (props != null){
            return props.getProperty("SONAR_ROOT_URL");
        }
        throw new Exception("Could not found SONAR_ROOT_URL");
    }

}
