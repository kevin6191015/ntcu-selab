package ntcu.selab.SpringServer.db.service;

import ntcu.selab.SpringServer.data.User;
import ntcu.selab.SpringServer.db.UserDBManager;
import java.util.ArrayList;
import java.util.List;

public class UserDBService {
    private static UserDBService object = new UserDBService();
    private UserDBManager udb = UserDBManager.getObject();

    public static UserDBService getObject(){
        return object;
    }

    public int getGitlabId(String username){
        return udb.getGitlabidByUsername(username);
    }

    public int getId(String username){
        return udb.getUseridByUsername(username);
    }

    public String getName(int id){
        return udb.getUsernameById(id);
    }
}
