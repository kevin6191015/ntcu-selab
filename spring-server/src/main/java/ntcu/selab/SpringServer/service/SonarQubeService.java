package ntcu.selab.SpringServer.service;

import ntcu.selab.SpringServer.config.GitlabConfig;
import ntcu.selab.SpringServer.config.JenkinsConfig;
import ntcu.selab.SpringServer.config.MysqlConfig;
import ntcu.selab.SpringServer.config.SonarConfig;
import ntcu.selab.SpringServer.db.QuestionDBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SonarQubeService {
    GitlabConfig gitlabConfig = GitlabConfig.getObject();

    SonarConfig sonarConfig = SonarConfig.getObject();

    JenkinsConfig jenkinsConfig = JenkinsConfig.getObject();

    MysqlConfig mysqlConfig = MysqlConfig.getObject();

    private static final Logger logger = LoggerFactory.getLogger(QuestionDBManager.class);

    public String getGitlabHost() throws Exception {
        return gitlabConfig.getGitlabHostUrl();
    }

    public String getSonarHost() throws Exception {
        return sonarConfig.getSonarHostUrl();
    }

    public String getSonarToken() throws Exception{
        return sonarConfig.getSonarApiToken();
    }


    public String getMySqlHost() throws Exception {
        return mysqlConfig.getDBUrl();
    }
}