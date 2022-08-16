package ntcu.selab.SpringServer.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ntcu.selab.SpringServer.data.User;

public class JwtConfig {
    private static String key = "MySecret";
    private static JwtConfig jwtConfig = null;
    private static final Logger logger = LoggerFactory.getLogger(JwtConfig.class);

    private JwtConfig(){
    }

    public static JwtConfig getObject(){
        if(jwtConfig == null){
            jwtConfig = new JwtConfig();
        }
        return jwtConfig;
    }

    public String generatorToken(User user){
        Date expireDate = new Date(System.currentTimeMillis()+ 2* 60 * 60 *1000);
            String jwtToken = Jwts.builder()
                .setSubject(user.getRole()) 
                .setExpiration(expireDate)
                .setIssuer("ntcu-selab")
                .setAudience(user.getUserName())
                .setId(user.getId())
                .claim("name", user.getName())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return jwtToken;
    }

    public boolean validateToken(String jwtToken){
        boolean isTrue = false;
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            isTrue = true;
        }catch(JwtException e){
            logger.error(e.getMessage());
        }
        return isTrue;
    }

    public Claims decodeToken(String jwtToken){
        if(this.validateToken(jwtToken)){
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
            return claims;
        }
        return null;
    }
}
