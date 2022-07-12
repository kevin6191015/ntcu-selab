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

    public String getDBUrl() throws Exception {
        String dbuser = System.getenv("DB_URL");
        if (dbuser != null && !dbuser.equals("")) {
            return dbuser;
        }
        if (props != null) {
            return props.getProperty("DB_URL").trim();
        }
        throw new Exception("Could not found DB_URL");
    }

}
