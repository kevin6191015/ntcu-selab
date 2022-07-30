package ntcu.selab.SpringServer.db;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

        if(user != null && user.getPassword().equals(login.getPassword())){
            //設定30min過期
            Date expireDate = new Date(System.currentTimeMillis()+ 30 * 60 * 1000);
            String jwtToken = Jwts.builder()
                .setSubject(login.getPassword()) //以password當subject
                .setExpiration(expireDate)
                //MySecret是自訂的私鑰，HS512是自選的演算法，可以任意改變
                .signWith(SignatureAlgorithm.HS512,"MySecret")
                .compact();
            return new Result(200, "", jwtToken);
        }

        return new Result(400, "登入失敗", "");
    }
}
