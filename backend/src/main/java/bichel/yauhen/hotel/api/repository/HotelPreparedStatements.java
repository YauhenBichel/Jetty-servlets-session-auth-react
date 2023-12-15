package bichel.yauhen.hotel.api.repository;

/**
 * Prepared statements for sql query execution
 */
public final class HotelPreparedStatements {

    private HotelPreparedStatements() {}

    /** For creating the hotels table */
    public static final String CREATE_HOTEL_TABLE =
            "CREATE TABLE hotels (" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(128) NOT NULL UNIQUE, " +
                    "address VARCHAR(128) NOT NULL, " +
                    "created DATETIME NOT NULL, " +
                    "modified DATETIME NOT NULL);";
}
