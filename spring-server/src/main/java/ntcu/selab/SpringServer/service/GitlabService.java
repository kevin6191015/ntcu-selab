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
import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GitlabService {
    private static final Logger logger = LoggerFactory.getLogger(GitlabConfig.class);
    private static final GitlabService object = new GitlabService();
    GitlabConfig gitlabConfig = GitlabConfig.getObject();
    private final GitlabAPI gitlab;
    private String hostUrl;
    private String rootUrl;
    private String apiToken;
    private final TokenType tokenType = TokenType.PRIVATE_TOKEN;
    private final AuthMethod authMethod = AuthMethod.URL_PARAMETER;
    private static final String API_NAMESPACE = "/api/v4";

    public GitlabService() {
        try {
            hostUrl = gitlabConfig.getGitlabHostUrl();
            rootUrl = gitlabConfig.getGitlabRootUrl();
            apiToken = gitlabConfig.getGitlabApiToken();
        } catch (Exception e) {
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
     * Delete a root project
     */
    public void deleteRootProject(String proName) {
        try {
            GitlabProject gitlabProject = gitlab.getProject("root", proName);
            gitlab.deleteProject(gitlabProject.getId());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
     * delete user by id
     */
    public void deleteUserByID(int userId) {
        try {
            gitlab.deleteUser(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
     * delete user by name
     */
    public void deleteUserByName(String username) {
        try {
            gitlab.deleteUser(getUserByName(username).getId());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
     * get current user
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
     * Get a private token by GitlabUser
     */
    public String getPrivateToken(@NonNull GitlabUser user) {
        String privateToken;
        privateToken = user.getPrivateToken();
        return privateToken;
    }

    /**
     * Get all user's list of projects
     */
    public List<GitlabProject> listAllProjects() {
        for (GitlabProject gitlabproject : gitlab.getAllProjects()) {
            System.out.println(gitlabproject);
        }
        return gitlab.getAllProjects();
    }

    /*
     * Get a new GitlabApi by user token
     */
    public GitlabAPI getUserApi(String token) {
        GitlabAPI newUser;
        newUser = GitlabAPI.connect(hostUrl, token, tokenType, authMethod);
        return newUser;
    }

    /*
     * get user by id
     */
    public GitlabUser getUserById(int userId) {
        GitlabUser gitlabUser = new GitlabUser();
        try {
            gitlabUser = gitlab.getUser(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return gitlabUser;
    }

    public GitlabUser getUserByName(String name) {
        GitlabUser gitlabUser = new GitlabUser();
        try {
            gitlabUser = gitlab.getUserViaSudo(name);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return gitlabUser;
    }

    /*
     * get user token by user id
     */
    public String getToken(int userGitlabId) {
        return getUserById(userGitlabId).getPrivateToken();
    }

    /**
     * get gitlab project by id
     */
    public GitlabProject getProjectById(int id) {
        GitlabProject project = null;
        try {
            project = gitlab.getProject(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return project;
    }

    /*
     * Get a list of project of user
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

    public GitlabProject getRootProject(String proName) {
        GitlabUser gitlabUser = getUserById(0);
        GitlabProject projects = new GitlabProject();
        try {
            projects = gitlab.getProject("root", proName);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return projects;
    }

    /*
     * get root
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
    /*
        update user email
     */
    public GitlabUser updateEmail(String email,String name) {
        GitlabUser user = null;
        HttpURLConnection conn = null;
        try {
            user = getUserByName(name);
            String urls = hostUrl + API_NAMESPACE +"/users/"+user.getId()+"?email="+email;
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            String input = gitlabConfig.getGitlabApiToken();
            String encoding = "Bearer " + input;
            conn.setRequestProperty("Authorization", encoding);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8), 8);
            String result = reader.readLine();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    /*
        update user password
     */
    public GitlabUser updatePassword(String password,String name) {
        GitlabUser user = null;
        try {
            user = getUserByName(name);
            gitlab.updateUser(user.getId(), user.getEmail(), password, user.getUsername(),
                    user.getName(), null, null, null, null, 100, null, null,
                    null, false, true, false);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }
    /*
    update user name
 */
    public GitlabUser updateUserName(String newName,String name) {
        GitlabUser user = null;
        HttpURLConnection conn = null;
        try {
            user = getUserByName(name);
            String urls = hostUrl + API_NAMESPACE +"/users/"+user.getId()+"?name="+newName;
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            String input = gitlabConfig.getGitlabApiToken();
            String encoding = "Bearer " + input;
            conn.setRequestProperty("Authorization", encoding);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8), 8);
            String result = reader.readLine();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    /**
     * Create a root project
     * http://120.108.204.152:10085/api/v4/projects?name=new_test2
     * &visibility=private&default_branch=main&initialize_with_readme=true
     */
    public GitlabProject createRootProject(String proName) {
        HttpURLConnection conn = null;
        try {
            String urls = hostUrl + API_NAMESPACE + "/projects?name=" + proName
                    + "&visibility=private&default_branch=main&initialize_with_readme=true";
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            String input = gitlabConfig.getGitlabApiToken();
            String encoding = "Bearer " + input;
            conn.setRequestProperty("Authorization", encoding);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8), 8);
            String result = reader.readLine();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return getRootProject(proName);
    }

    /**
     * set gitlab Integrations
     */
    public void setGitlabIntegrations(GitlabProject project, String jobName) {
        try {
            HttpURLConnection conn = null;

            JenkinsConfig jenkinsConfig = JenkinsConfig.getObject();
            String jenkinsJobUrl = jenkinsConfig.getJenkinsHostUrl();

            // for example,
            // http://localhost:80/api/v4/projects/3149/hooks?url=http://localhost:8888/project/{jobName}
            String gitlabWebhookApi = hostUrl + API_NAMESPACE + "/projects/" + project.getId()
                    + "/integrations/jenkins?jenkins_url="
                    + jenkinsJobUrl + "&project_name=" + jobName + "&username=" + jenkinsConfig.getJenkinsRootUsername()
                    + "&password=" + jenkinsConfig.getJenkinsRootPassword();
            URL url = new URL(gitlabWebhookApi);
            conn = (HttpURLConnection) url.openConnection();
            String input = gitlabConfig.getGitlabApiToken();
            String encoding = "Bearer " + input;
            conn.setRequestProperty("Authorization", encoding);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8), 8);
            String result = reader.readLine();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
     * add member to project
     */
    public void addMember(GitlabUser user, String proName) {
        HttpURLConnection conn = null;
        try {
            String urls = hostUrl + API_NAMESPACE + "/projects/" + getRootProject(proName).getId()
                    + "/invitations?access_level=40&user_id=" + user.getId() + "&email=" + user.getEmail();
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            String input = gitlabConfig.getGitlabApiToken();
            String encoding = "Bearer " + input;
            conn.setRequestProperty("Authorization", encoding);
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.connect();
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8), 8);
            String result = reader.readLine();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /*
     * get project url
     */
    public String getProjectUrl(String username, String projectName) {
        return hostUrl + "/" + username + "/" + projectName + ".git";
    }

    /**
     * Create a new User
     */
    public GitlabUser createUser(String email, String password, String username, String name) throws IOException {
        GitlabUser user = null;
        try {
            user = new GitlabUser();
            user = gitlab.createUser(email, password, username, name, "", "", "", "", 100, null, null, "",
                    false, true, true, false);

            String privateToken = getToken(user.getId());
            user.setPrivateToken(privateToken);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return user;
    }

}
