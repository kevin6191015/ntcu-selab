package ntcu.selab.SpringServer.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ntcu.selab.SpringServer.data.Login;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.db.LoginDBManager;

@RestController
public class LoginService {
    private LoginDBManager lDbManager = LoginDBManager.getObject();
    private JenkinsService jenkinsService = JenkinsService.getObject();
    @PostMapping("/login")
    public Result login(@RequestBody Login login) throws Exception{
        System.out.println("adsd");
        jenkinsService.createJob("dsfsdfaaa");
        return lDbManager.login(login);
    }
}
