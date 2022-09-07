package ntcu.selab.SpringServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SpringConfig {
    private static final String PROPERTY_FILE = "/spring.properties";
    private Properties props;
    private static  SpringConfig object = new SpringConfig();
    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    public SpringConfig() {
        InputStream is = this.getClass().getResourceAsStream(PROPERTY_FILE);
        try {
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    public static SpringConfig getObject() {
        return object;
    }

    public String getSpringHostUrl() throws Exception {
        String springHost = System.getenv("SPRING_HOST_URL");
        if (springHost != null && !springHost.equals("")) {
            return springHost;
        }
        if (props != null) {
            return props.getProperty("SPRING_HOST_URL").trim();
        }
        throw new Exception("Could not found SPRING_HOST_URL");
    }
    public String getSpringRootUserName() throws Exception {
        String springName = System.getenv("SPRING_ROOT_USERNAME");
        if (springName != null && !springName.equals("")) {
            return springName;
        }
        if (props != null) {
            return props.getProperty("SPRING_ROOT_USERNAME").trim();
        }
        throw new Exception("Could not found SPRING_ROOT_USERNAME");
    }
    public String getSpringRootUserPassword() throws Exception {
        String springPassword = System.getenv("SPRING_ROOT_PASSWORD");
        if (springPassword != null && !springPassword.equals("")) {
            return springPassword;
        }
        if (props != null) {
            return props.getProperty("SPRING_ROOT_PASSWORD").trim();
        }
        throw new Exception("Could not found SPRING_ROOT_PASSWORD");
    }
}
