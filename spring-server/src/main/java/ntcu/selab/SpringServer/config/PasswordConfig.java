package ntcu.selab.SpringServer.config;

import java.util.Base64;
import java.io.UnsupportedEncodingException;

public class PasswordConfig {
    private static PasswordConfig  passwordConfig = null;

    public static PasswordConfig getObject(){
        if(passwordConfig == null){
            passwordConfig = new PasswordConfig();
        }
        return passwordConfig;
    }

    public String encrypt(String password) 
    {   
        String base64encodedString = null;
        try {
            base64encodedString = Base64.getEncoder().encodeToString(password.getBytes("utf-8"));
        }catch(UnsupportedEncodingException e){
            System.out.println("Error :" + e.getMessage());
        }
        return base64encodedString;
    }
    
    public String decrypt(String password){
        String DecodeText = null;
        try {
            DecodeText = new String(Base64.getDecoder().decode(password), "UTF-8");
        }catch(UnsupportedEncodingException e){
            System.out.println("Error :" + e.getMessage());
        }
        return DecodeText;
    }
}
