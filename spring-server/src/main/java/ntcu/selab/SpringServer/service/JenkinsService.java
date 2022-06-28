package ntcu.selab.SpringServer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        
    }
}
