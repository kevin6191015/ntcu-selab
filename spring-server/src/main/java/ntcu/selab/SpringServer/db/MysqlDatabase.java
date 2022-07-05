package ntcu.selab.SpringServer.db;

import java.sql.Connection;
import java.sql.SQLException;

import ntcu.selab.SpringServer.config.MysqlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class MysqlDatabase {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final Logger logger = LoggerFactory.getLogger(MysqlDatabase.class);
    private static MysqlDatabase object = new MysqlDatabase();
    private static BoneCP connectionPool = null;

    private MysqlDatabase(){
        try{
            Class.forName(DB_DRIVER);
            if(connectionPool == null){
                BoneCPConfig config = getConfig();
                connectionPool = new BoneCP(config);
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }
    }

    public static MysqlDatabase getObject(){
        return object;
    }

    private BoneCPConfig getConfig(){
        BoneCPConfig config = new BoneCPConfig();
        try{
            String connection = MysqlConfig.getObject().getConnectionString();
            String user = MysqlConfig.getObject().getDBUser();
            String password = MysqlConfig.getObject().getDBPassword();

            config.setJdbcUrl(connection);
            config.setUsername(user);
            config.setPassword(password);

            config.setPartitionCount(3);
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(20);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return config;
    }

    public Connection getConnection(){
        Connection connection = null;
        if(connectionPool == null){
            BoneCPConfig config = getConfig();
            try{
                connectionPool = new BoneCP(config);
            }catch(SQLException e){
                e.printStackTrace();;
                throw new NullPointerException();
            }
        }
        try {
            connection = connectionPool.getConnection();
        } catch (SQLException e) {
            connectionPool = null;
            e.printStackTrace();
            throw new NullPointerException();
        }
        return connection;
    }
}
