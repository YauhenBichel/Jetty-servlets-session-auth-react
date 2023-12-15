package bichel.yauhen.hotel.api.db;

import bichel.yauhen.hotel.api.repository.AccountPreparedStatements;
import bichel.yauhen.hotel.api.repository.HotelPreparedStatements;
import bichel.yauhen.hotel.api.repository.ReviewPreparedStatements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class SchemaGeneration {
    private static final Logger logger = LogManager.getLogger(SchemaGeneration.class);

    private final SqlConnectionPool sqlConnectionPool;

    public SchemaGeneration(SqlConnectionPool sqlConnectionPool) {
        this.sqlConnectionPool = sqlConnectionPool;
    }

    public void createTables() {
        try (Connection dbConnection = sqlConnectionPool.getConnection()) {
            logger.info("dbConnection successful");
            try (Statement statement = dbConnection.createStatement()) {
                try{
                    statement.executeUpdate(AccountPreparedStatements.CREATE_USER_TABLE);
                } catch (SQLException ex) {
                    logger.error(ex);
                }
                try {
                    statement.executeUpdate(HotelPreparedStatements.CREATE_HOTEL_TABLE);
                } catch (SQLException ex) {
                    logger.error(ex);
                }
                try {
                    statement.executeUpdate(ReviewPreparedStatements.CREATE_REVIEW_TABLE);
                } catch (SQLException ex) {
                    logger.error(ex);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
    }
}
