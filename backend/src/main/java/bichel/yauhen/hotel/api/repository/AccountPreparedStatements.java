package bichel.yauhen.hotel.api.repository;

/**
 * Prepared statements for sql query execution
 */
public final class AccountPreparedStatements {

    private AccountPreparedStatements() {}

    /** For creating the users table */
    public static final String CREATE_USER_TABLE =
            "CREATE TABLE users (" +
                    "userid INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(32) NOT NULL UNIQUE, " +
                    "password CHAR(64) NOT NULL, " +
                    "usersalt CHAR(32) NOT NULL, " +
                    "created DATETIME NOT NULL, " +
                    "modified DATETIME NOT NULL);";

    /** Used to insert a new user into the database. */
    public static final String REGISTER_SQL =
            "INSERT INTO users (username, password, usersalt, created, modified) " +
                    "VALUES (?, ?, ?, ?, ?);";

    /** Used to retrieve the salt associated with a specific user. */
    public static final String SALT_SQL =
            "SELECT usersalt FROM users WHERE username = ?";

    /** Used to authenticate a user. */
    public static final String AUTH_SQL =
            "SELECT userid, username, password, created, modified FROM users " +
                    "WHERE username = ? AND password = ?";

}
