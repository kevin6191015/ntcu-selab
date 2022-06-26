package ntcu.selab.SpringServer.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.el.ELException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.AttributeAccessor;


public class JenkinsConfig {
    private static String PROPERTY_FILE = "/jenkins.properties";
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
            return props.getProperty("JENKINS_HOST_URL");
        }
        throw new Exception("Could not found JENKINS_HOST_URL");
    }
}
