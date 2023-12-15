package bichel.yauhen.hotel.api.repository;

import bichel.yauhen.hotel.api.db.SqlConnectionPool;
import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.utils.HashUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Jdbc implementation of hotel repository
 */
public class JdbcHotelRepository implements AccountRepository {

    private static final Logger logger = LogManager.getLogger(JdbcHotelRepository.class);
    private final SqlConnectionPool sqlConnectionPool;

    public JdbcHotelRepository(SqlConnectionPool sqlConnectionPool) {
        this.sqlConnectionPool = sqlConnectionPool;
    }

    /**
     * Registers a new user, placing the username, password hash, and
     * salt into the database.
     *
     * @param account - Account
     */
    @Override
    public Optional<Account> create(Account account) {
        String usersalt = HashUtils.encodeHex(HashUtils.generateSalt(), 32); // salt
        String passhash = HashUtils.getSHA256Hash(account.getPassword(), usersalt); // hashed password

        Account dbAccount = new Account();

        PreparedStatement statement = null;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Register: dbConnection successful");
            try {
                statement = connection.prepareStatement(AccountPreparedStatements.REGISTER_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, account.getUsername());
                statement.setString(2, passhash);
                statement.setString(3, usersalt);

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dbAccount.setId(generatedKeys.getInt(1));
                        dbAccount.setPassword(passhash);
                        dbAccount.setUsername(account.getUsername());
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

                statement.close();
            } catch (SQLException ex) {
                logger.error(ex);
                return Optional.empty();
            }
            finally {
                sqlConnectionPool.releaseConnection(connection);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            return Optional.empty();
        }

        return Optional.of(dbAccount);
    }

    public Optional<Account> get(Account account) {
        PreparedStatement statement;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Authenticate: dbConnection successful");
            statement = connection.prepareStatement(AccountPreparedStatements.AUTH_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            String usersalt = getSalt(connection, account.getUsername());
            String passhash = HashUtils.getSHA256Hash(account.getPassword(), usersalt);

            statement.setString(1, account.getUsername());
            statement.setString(2, passhash);
            ResultSet results = statement.executeQuery();
            boolean exists = results.next();
            if(exists) {
                return Optional.of(account);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return Optional.empty();
    }

    /**
     * Gets the salt for a specific user.
     *
     * @param connection - active database connection
     * @param user       - which user to retrieve salt for
     * @return salt for the specified user or null if user does not exist
     * @throws SQLException if any issues with database connection
     */
    private String getSalt(Connection connection, String user) {
        String salt = null;
        try (PreparedStatement statement = connection.prepareStatement(AccountPreparedStatements.SALT_SQL)) {
            statement.setString(1, user);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                salt = results.getString("usersalt");
                return salt;
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }

        return salt;
    }

    @Override
    public Optional<Account> remove(Account account) {
        return Optional.empty();
    }
}
