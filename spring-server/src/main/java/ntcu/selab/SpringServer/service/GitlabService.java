package ntcu.selab.SpringServer.service;

import ntcu.selab.SpringServer.config.GitlabConfig;
import org.gitlab.api.GitlabAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitlabService {
    private static final Logger logger = LoggerFactory.getLogger(GitlabConfig.class);
    private static GitlabService object = new GitlabService();
    GitlabConfig gitlabConfig = GitlabConfig.getObject();
    private GitlabAPI gitlab;
    private String hostUrl;

    private GitlabService() {
        try{
            hostUrl = gitlabConfig.getGitlabHostUrl();
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public static GitlabService getObject() {
        return object;
    }

    public GitlabAPI getGitlab() {
        return gitlab;
    }
}
