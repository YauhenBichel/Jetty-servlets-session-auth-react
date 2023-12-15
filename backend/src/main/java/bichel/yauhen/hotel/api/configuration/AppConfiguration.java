package bichel.yauhen.hotel.api.configuration;

import bichel.yauhen.hotel.api.db.MySqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppConfiguration {
    private static final Logger logger = LogManager.getLogger(AppConfiguration.class);

    private static final int DEFAULT_PORT = 8080;
    private int appPort;

    public void loadAppProperties(String propertyFile) {
        Properties config = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (FileReader fr = new FileReader(classLoader.getResource(propertyFile).getFile())) {
            config.load(fr);
        } catch (IOException ex) {
            logger.error(ex);
        }

        if(config.getProperty("app.port") != null) {
            appPort = Integer.parseInt(config.getProperty("app.port"));
        } else {
            appPort = DEFAULT_PORT;
        }
    }

    public int getAppPort() {
        return this.appPort;
    }
}
