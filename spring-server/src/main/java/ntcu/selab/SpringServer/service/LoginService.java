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

    @PostMapping("/login")
    @CrossOrigin
    public Result login(@RequestBody Login login) throws Exception{
        return lDbManager.login(login);
    }
}
