package ntcu.selab.SpringServer.config;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlConfig {
    private static final String PROPERTY_FILE = "/mysql.properties";
    private static MysqlConfig object = new MysqlConfig();
    private static final Logger logger = LoggerFactory.getLogger(MysqlConfig.class);
    private Properties props;

    public MysqlConfig() {
        InputStream is = this.getClass().getResourceAsStream(PROPERTY_FILE);
        try {
            props = new Properties();
            props.load(is);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static MysqlConfig getObject() {
        return object;
    }

    public String getDBUser() throws Exception {
        String dbuser = System.getenv("DB_USER");
        if (dbuser != null && !dbuser.equals("")) {
            return dbuser;
        }
        if (props != null) {
            return props.getProperty("DB_USER").trim();
        }
        throw new Exception("Could not found DB_USER");
    }

    public String getDBPassword() throws Exception {
        String dbpassword = System.getenv("DB_PASSWORD");
        if (dbpassword != null && !dbpassword.equals("")) {
            return dbpassword;
        }
        if (props != null) {
            return props.getProperty("DB_PASSWORD").trim();
        }
        throw new Exception("Could not found DB_PASSWORD");
    }

    public String getDBSchema() throws Exception {
        String dbschema = System.getenv("DB_DATABASE");
        if (dbschema != null && !dbschema.equals("")) {
            return dbschema;
        }
        if (props != null) {
            return props.getProperty("DB_DATABASE").trim();
        }
        throw new Exception("Could not found DB_DATABASE");
    }

    public String getDBHost() throws Exception {
        String dbhost = System.getenv("DB_HOST");
        if (dbhost != null && !dbhost.equals("")) {
            return dbhost;
        }
        if (props != null) {
            return props.getProperty("DB_HOST").trim();
        }
        throw new Exception("Could not found DB_HOST");
    }

    public String getDBConnectionOpt() throws Exception {
        String dbconnopt = System.getenv("DB_CONNECTION_OPTION");
        if (dbconnopt != null && !dbconnopt.equals("")) {
            return dbconnopt;
        }
        if (props != null) {
            return props.getProperty("DB_CONNECTION_OPTION").trim();
        }
        throw new Exception("Could not found DB_CONNECTION_OPTION");
    }

    public String getConnectionString() throws Exception {
        return "jdbc:mysql://" + getDBHost() + "/" + getDBSchema() + getDBConnectionOpt();
    }
}
