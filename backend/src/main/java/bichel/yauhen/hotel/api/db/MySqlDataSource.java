package bichel.yauhen.hotel.api.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MySqlDataSource implements SqlDataSource {

    private static final Logger logger = LogManager.getLogger(MySqlDataSource.class);

    private String jdbcUrl;
    private String dbUsername;
    private String dbPassword;

    public void loadDatabaseProperties(String propertyFile) {
        Properties config = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (FileReader fr = new FileReader(classLoader.getResource(propertyFile).getFile())) {
            config.load(fr);
        } catch (IOException ex) {
            logger.error(ex);
        }

        jdbcUrl = config.getProperty("jdbc.url");
        dbUsername = config.getProperty("jdbc.username");
        dbPassword = config.getProperty("jdbc.password");
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public String getDbUsername() {
        return this.dbUsername;
    }

    public String getDbPassword() {
        return this.dbPassword;
    }
}
