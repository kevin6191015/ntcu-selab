package ntcu.selab.SpringServer;

import ntcu.selab.SpringServer.config.GitlabConfig;
import ntcu.selab.SpringServer.service.GitlabService;
import ntcu.selab.SpringServer.config.JenkinsConfig;
import ntcu.selab.SpringServer.service.JenkinsService;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringServerController {

    @GetMapping ("/create")
    public String hello() throws Exception {
        /*JenkinsService temp = JenkinsService.getObject();
        temp.createJob("test");
        GitlabService gitlab = GitlabService.getObject();
        GitlabProject project = gitlab.createRootProject("test");
        gitlab.setGitlabIntegrations(project,"test");
        gitlab.addMember(gitlab.getUserByName("a001"),"test");*/
        GitlabService gitlab = GitlabService.getObject();
        GitlabUser user = new GitlabUser() ;
        user = gitlab.createUser("examplea@gmail.com","12345678","a005","temp");
        return "OK";
    }
    @GetMapping ("/delete")
    public String hello2(){
        GitlabService gitlab = GitlabService.getObject();
        gitlab.deleteRootProject("test");
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
