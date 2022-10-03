package ntcu.selab.SpringServer.db;

import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDatabase.class);
    private static MysqlDatabase object = new MysqlDatabase();

    private MysqlDatabase(){

    }

    public static MysqlDatabase getObject(){
        return object;
    }

    public synchronized HttpURLConnection getConnection(URL url, String httpmethod){
        HttpURLConnection conn = null;
        try{
            Thread.sleep(300);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpmethod);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return conn;
    }
}
