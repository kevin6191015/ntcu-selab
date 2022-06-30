package ntcu.selab.SpringServer.service;

import ntcu.selab.SpringServer.config.GitlabConfig;
import ntcu.selab.SpringServer.config.JenkinsConfig;
import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
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
        gitlab = GitlabAPI.connect(hostUrl, apiToken, tokenType, authMethod);
    }

    public static GitlabService getObject() {
        return object;
    }

    public GitlabAPI getGitlab() {
        return gitlab;
    }


    /*
        Delete a root project
     */
    public Boolean deleteRootProject(String proName) {
        try {
            GitlabProject gitlabProject = gitlab.getProject("root", proName);
            gitlab.deleteProject(gitlabProject.getId());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return true;
    }
    /*
    delete user by id
     */
    public void deleteUser(int userId) {
        try {
            gitlab.deleteUser(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    /*
        get current user
     */
    public GitlabUser getUser() {
        GitlabUser gitlabUser = new GitlabUser();
        try {
            gitlabUser = gitlab.getUser();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return gitlabUser;
    }

    /**
     Get a private token by GitlabUser
     */
    public String getPrivateToken(GitlabUser user) {
        String privateToken;
        privateToken = user.getPrivateToken();
        return privateToken;
    }

    /**
      Get all user's list of projects
     */
    public List<GitlabProject> getAllProjects() {
        for(GitlabProject gitlabproject : gitlab.getAllProjects()){
            System.out.println(gitlabproject);
        }
        return gitlab.getAllProjects();
    }
    /*
        Get a new GitlabApi by user token
     */
    public GitlabAPI getUserApi(String token) {
        GitlabAPI newUser;
        newUser = GitlabAPI.connect(hostUrl, token, tokenType, authMethod);
        return newUser;
    }
    /*
        get user by id
     */
    public GitlabUser getUserById(int userId) {
        GitlabUser gitlabUser = new GitlabUser();
        try {
            gitlabUser = gitlab.getUser(userId);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        return gitlabUser;
    }

    /*
        get user token by user id
     */
    public String getToken(int userGitlabId) {
        return getUserById(userGitlabId).getPrivateToken();
    }
    /**
     get gitlab project by id
     */
    public GitlabProject getProjectbyid(int id) {
        GitlabProject project = null;
        try {
            project = gitlab.getProject(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return project;
    }

    /*
      Get a list of project of user
     */
    public List<GitlabProject> getProject(int userGitlabId) {
        GitlabUser gitlabUser = getUserById(userGitlabId);
        List<GitlabProject> projects = new ArrayList<>();
        try {
            projects = gitlab.getProjectsViaSudo(gitlabUser);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return projects;
    }
    /*
        get root
     */
    public GitlabUser getRoot() {
        GitlabUser root = new GitlabUser();
        try {
            root = gitlab.getUser(1);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return root;
    }
    /**
     * Create a root project
     */
    public GitlabProject createRootProject(String proName) {
        GitlabProject project = null;
        try {
            project = gitlab.createUserProject(1, proName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return project;
    }

    /**
     * set gitlab web hook
     */
    public boolean setGitlabWebhook(GitlabProject project, String jobName){
        try {
            HttpURLConnection conn = null;

            JenkinsConfig jenkinsConfig = JenkinsConfig.getObject();
            String jenkinsJobUrl = jenkinsConfig.getJenkinsHostUrl() + "/project/" + jobName;

            // for example,
            // http://localhost:80/api/v4/projects/3149/hooks?url=http://localhost:8888/project/{jobName}
            String gitlabWebhookApi = hostUrl + API_NAMESPACE + "/projects/" + project.getId() + "jenkins?jenkins_url="
                    + jenkinsJobUrl + "&project_name=" + jobName;
            URL url = new URL(gitlabWebhookApi);
            conn = (HttpURLConnection) url.openConnection();
            String input = gitlabConfig.getGitlabRootUsername() + ":" + gitlabConfig.getGitlabRootPassword();
            Base64.Encoder encoder = Base64.getEncoder();
            String encoding = "Basic " + encoder.encodeToString(input.getBytes());
            conn.setRequestProperty("Authorization", encoding);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
            String result = reader.readLine();
        }catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
    /*
    get project url
     */
    public String getProjectUrl(String username, String projectName) {
        return hostUrl + "/" + username + "/" + projectName + ".git";
    }

    /**
      Create a new User
     */
    public GitlabUser createUser(String email, String password, String username, String name) throws Exception {
        GitlabUser user = new GitlabUser();
        user = gitlab.createUser(email, password, username, name, "", "", "", "", 100, null, null, "",
                false, true, true, false);

        String privateToken = getToken(user.getId());
        user.setPrivateToken(privateToken);
        return user;
    }

}
