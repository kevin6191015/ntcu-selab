package ntcu.selab.SpringServer;

import ntcu.selab.SpringServer.config.GitlabConfig;
import ntcu.selab.SpringServer.service.GitlabService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringServerController {

    @GetMapping ("/")
    public String hello() throws Exception {
        GitlabConfig temp = new GitlabConfig();
        return temp.getGitlabRootUrl();
    }
}
