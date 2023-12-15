package bichel.yauhen.hotel.api.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlConnectionPool {
    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
    void shutdown() throws SQLException;
}
