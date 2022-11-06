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
            Thread.sleep(50);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(httpmethod);
<<<<<<< HEAD
            conn.setConnectTimeout(100000);
            conn.setReadTimeout(100000);
=======
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
>>>>>>> 00b5c145b720d05d4ddff930d83f115c3fb8d387
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return conn;
    }
}
