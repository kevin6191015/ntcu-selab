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

}
