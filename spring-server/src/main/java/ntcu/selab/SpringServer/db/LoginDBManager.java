package ntcu.selab.SpringServer.db;

import ntcu.selab.SpringServer.data.Login;
import ntcu.selab.SpringServer.data.Result;
import ntcu.selab.SpringServer.data.User;

public class LoginDBManager {
    private static UserDBManager uDbManager = UserDBManager.getObject();
    private static LoginDBManager lDbManager = null;

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

        String uid = uDbManager.getUseridByUsername(login.getUsername());
        User user = uDbManager.getUserInfo(uid);

        if(user != null && user.getPassword().equals(login.getUsername())){
            return new Result(200, "", user);
        }

        return new Result(400, "登入失敗", "");
    }
}
