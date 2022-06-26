package ntcu.selab.SpringServer.service;

import ntcu.selab.SpringServer.config.GitlabConfig;
import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GitlabService {
    private static final Logger logger = LoggerFactory.getLogger(GitlabConfig.class);
    private static GitlabService object = new GitlabService();
    GitlabConfig gitlabConfig = GitlabConfig.getObject();
    private GitlabAPI gitlab;
    private String hostUrl;
    private String rootUrl;
    private String apiToken;
    private TokenType tokenType = TokenType.PRIVATE_TOKEN;
    private AuthMethod authMethod = AuthMethod.URL_PARAMETER;
    private static final String API_NAMESPACE = "/api/v4";

    public GitlabService() {
        try{
            hostUrl = gitlabConfig.getGitlabHostUrl();
            rootUrl = gitlabConfig.getGitlabRootUrl();
            apiToken = gitlabConfig.getGitlabApiToken();
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
    public GitlabUser getRoot() {
        GitlabUser root = new GitlabUser();
        try {
            root = gitlab.getUser(1);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return root;
    }

}
