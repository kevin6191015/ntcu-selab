package ntcu.selab.SpringServer.db;

import ntcu.selab.SpringServer.config.JwtConfig;
import ntcu.selab.SpringServer.config.PasswordConfig;
import ntcu.selab.SpringServer.data.Login;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.User;

public class LoginDBManager {
    private static UserDBManager uDbManager = UserDBManager.getObject();
    private static LoginDBManager lDbManager = null;
    private static PasswordConfig passwordConfig = PasswordConfig.getObject();
    private static JwtConfig jwtConfig = JwtConfig.getObject();

    public static LoginDBManager getObject(){
        if(lDbManager == null){
            lDbManager = new LoginDBManager();
        }
        return lDbManager;
    }


    public Result login(Login login) throws Exception{
        if(login.getUsername().isEmpty()){
            return new Result(400, "帳號不能是空白", "");
        }

        if(login.getPassword().isEmpty()){
            return new Result(400, "密碼不能是空白", "");
        }

        String uid = null;
        if((uid = uDbManager.getUseridByUsername(login.getUsername())) == null){
            return new Result(400, "用戶不存在", "");
        }
        
        User user = uDbManager.getUserInfo(uid);
        String password = user.getPassword();
        if(user == null || !passwordConfig.decrypt(password).equals(login.getPassword())){
            return new Result(400, "密碼錯誤", "");
        }

        String jwtToken = jwtConfig.generatorToken(user);

        Login loginInfo = new Login(login.getUsername(), login.getPassword());
        loginInfo.setToken(jwtToken);
        loginInfo.setUser(user);

        return new Result(200, "", loginInfo);        
    }
}
