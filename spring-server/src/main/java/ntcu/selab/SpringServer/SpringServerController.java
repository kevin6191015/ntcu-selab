package ntcu.selab.SpringServer;

import ntcu.selab.SpringServer.config.GitlabConfig;
import ntcu.selab.SpringServer.service.GitlabService;
import ntcu.selab.SpringServer.config.JenkinsConfig;
import ntcu.selab.SpringServer.service.JenkinsService;
import org.gitlab.api.models.GitlabProject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringServerController {

    @GetMapping ("/")
    public String hello() throws Exception {
        JenkinsService temp = JenkinsService.getObject();
        temp.createJob("a");
        return "ok";
    }
    /*@GetMapping ("/config")
    public String hello2() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabApiToken();
    }
    @GetMapping ("/config1")
    public String hello3() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabRootUrl();
    }
    @GetMapping ("/config2")
    public String hello4() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabHostUrl();
    }*/
}
