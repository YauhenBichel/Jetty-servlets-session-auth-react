package bichel.yauhen.hotel.api.db;

import java.sql.DriverManager;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasicConnectionPool implements SqlConnectionPool {

    private static final int MAX_POOL_SIZE = 10;
    //default 30 seconds
    private static final int MAX_TIMEOUT = 30;

    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();

    private BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    public static BasicConnectionPool create(
            SqlDataSource sqlDataSource) throws SQLException {

        List<Connection> pool = new ArrayList<>(MAX_POOL_SIZE);
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            pool.add(createConnection(sqlDataSource.getJdbcUrl(), sqlDataSource.getDbUsername(), sqlDataSource.getDbPassword()));
        }
        return new BasicConnectionPool(sqlDataSource.getJdbcUrl(), sqlDataSource.getDbUsername(), sqlDataSource.getDbPassword(), pool);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                Connection connection = usedConnections
                        .remove(usedConnections.size() - 1);
                connectionPool.add(connection);
            }
        }

        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);

        if (!connection.isValid(MAX_TIMEOUT)) {
            connection = createConnection(url, user, password);
        }

        usedConnections.add(connection);
        return connection;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
