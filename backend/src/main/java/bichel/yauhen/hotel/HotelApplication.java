package bichel.yauhen.hotel;

import bichel.yauhen.hotel.api.ServletLocator;
import bichel.yauhen.hotel.api.configuration.AppConfiguration;
import bichel.yauhen.hotel.api.controller.HotelReviewServlet;
import bichel.yauhen.hotel.api.controller.HotelServlet;
import bichel.yauhen.hotel.api.controller.LogoutServlet;
import bichel.yauhen.hotel.api.controller.HotelsByWordServlet;
import bichel.yauhen.hotel.api.db.BasicConnectionPool;
import bichel.yauhen.hotel.api.db.MySqlDataSource;
import bichel.yauhen.hotel.api.controller.LoginServlet;
import bichel.yauhen.hotel.api.controller.RegistrationServlet;
import bichel.yauhen.hotel.api.db.SchemaGeneration;
import bichel.yauhen.hotel.api.db.SqlConnectionPool;
import bichel.yauhen.hotel.api.db.SqlDataSource;
import bichel.yauhen.hotel.api.server.JettyHotelServer;
import bichel.yauhen.hotel.cli.enumeration.CliPathQueryKeyEnum;
import bichel.yauhen.hotel.core.HotelReviewsLoader;
import bichel.yauhen.hotel.core.data.ThreadSafeHotelReviewData;
import bichel.yauhen.hotel.core.data.ThreadSafeHotelReviewDataForReading;
import bichel.yauhen.hotel.core.processor.HotelsProcessor;
import bichel.yauhen.hotel.core.processor.ReviewsProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Hotel Application. Entry point
 */
public class HotelApplication {
    private static final Logger logger = LogManager.getLogger(HotelApplication.class);
    private static final String APP_PROPERTIES_FILE = "app.properties";

    public static void main(String[] args) {

        try {
            //-hotels input/hotels/hotels.json -reviews input/reviews -threads 3
            Map<CliPathQueryKeyEnum, String> keyValuePathMap = new HashMap<>();
            for (int i = 0; i < args.length - 1; i += 2) {
                keyValuePathMap.put(CliPathQueryKeyEnum.enumByValue(args[i]), args[i + 1]);
            }

            HotelReviewsLoader dataLoader = new HotelReviewsLoader(new HotelsProcessor(),
                    new ReviewsProcessor(),
                    new ThreadSafeHotelReviewData(),
                    keyValuePathMap);

            ThreadSafeHotelReviewDataForReading hotelReviewData = dataLoader.load();

            SqlDataSource sqlDataSource = new MySqlDataSource();
            sqlDataSource.loadDatabaseProperties(APP_PROPERTIES_FILE);
            SqlConnectionPool sqlConnectionPool = BasicConnectionPool.create(sqlDataSource);

            SchemaGeneration schemaGeneration = new SchemaGeneration(sqlConnectionPool);
            schemaGeneration.createTables();

            AppConfiguration appConfiguration = new AppConfiguration();
            appConfiguration.loadAppProperties(APP_PROPERTIES_FILE);

            ServletLocator servletLocator = new ServletLocator(sqlConnectionPool, hotelReviewData);

            JettyHotelServer server = new JettyHotelServer(appConfiguration.getAppPort());
            server.addHandler("/register", servletLocator.getHttpServlet(RegistrationServlet.class));
            server.addHandler("/login", servletLocator.getHttpServlet(LoginServlet.class));
            server.addHandler("/logout", servletLocator.getHttpServlet(LogoutServlet.class));
            server.addHandler("/hotels-by-word", servletLocator.getHttpServlet(HotelsByWordServlet.class));
            server.addHandler("/hotel", servletLocator.getHttpServlet(HotelServlet.class));
            server.addHandler("/hotel-review", servletLocator.getHttpServlet(HotelReviewServlet.class));

            server.start();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}
