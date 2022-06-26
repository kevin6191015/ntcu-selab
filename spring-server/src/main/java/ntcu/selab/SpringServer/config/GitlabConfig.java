package ntcu.selab.SpringServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class GitlabConfig {
    private static final String PROPERTY_FILE = "/gitlab.properties";
    private Properties props;
    private static  GitlabConfig object = new GitlabConfig();
    private static final Logger logger = LoggerFactory.getLogger(GitlabConfig.class);

    public GitlabConfig() {
        InputStream is = this.getClass().getResourceAsStream(PROPERTY_FILE);
        try {
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static GitlabConfig getObject() {
        return object;
    }

    public String getGitlabHostUrl() throws Exception {
        String gitlabHost = System.getenv("GITLAB_HOST");
        if (gitlabHost != null && !gitlabHost.equals("")) {
            return gitlabHost;
        }
        if (props != null) {
            return props.getProperty("GITLAB_HOST_URL").trim();
        }
        throw new Exception("Could not found GITLAB_HOST_URL");
    }

    public String getGitlabRootUsername() throws Exception {
        String gitlabRootUsername = System.getenv("GITLAB_Root_USERNAME");

        if (gitlabRootUsername != null && !gitlabRootUsername.equals("")) {
            return gitlabRootUsername;
        }
        if (props != null) {
            return props.getProperty("GITLAB_ROOT_USERNAME").trim();
        }
        throw new Exception("Could not found GITLAB_ROOT_USERNAME");
    }

    public String getGitlabRootPassword() throws Exception {
        String gitlabRootPassword = System.getenv("GITLAB_ROOT_PASSWORD");
        if (gitlabRootPassword != null && !gitlabRootPassword.equals("")) {
            return gitlabRootPassword;
        }
        if (props != null) {
            return props.getProperty("GITLAB_ROOT_PASSWORD").trim();
        }
        throw new Exception("Could not found GITLAB_ROOT_PASSWORD");
    }

    public String getGitlabApiToken() throws Exception {
        String gitlabApiToken = System.getenv("GITLAB_Root_TOKEN");
        if (gitlabApiToken != null && !gitlabApiToken.equals("")) {
            return gitlabApiToken;
        }
        if (props != null) {
            return props.getProperty("GITLAB_API_TOKEN").trim();
        }
        throw new Exception("Could not found GITLAB_API_TOKEN");
    }

    public String getGitlabRootUrl() throws Exception {
        String gitlabHost = "http://120.108.204.152:10085";
        if (gitlabHost != null && !gitlabHost.equals("")) {
            return gitlabHost.substring(0, gitlabHost.indexOf("//") + 2)
                    + getGitlabRootUsername()
                    + ":"
                    + getGitlabRootPassword()
                    + "@"
                    + gitlabHost.substring(gitlabHost.indexOf("//") + 2, gitlabHost.length());
        }
        if (props != null) {
            return props.getProperty("GITLAB_ROOT_URL").trim();
        }
        throw new Exception("Could not found GITLAB_HOST");
    }

}
