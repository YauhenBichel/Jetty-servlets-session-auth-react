package bichel.yauhen.hotel.api.repository;

/**
 * Prepared statements for sql query execution
 */
public final class ReviewPreparedStatements {

    private ReviewPreparedStatements() {}

    /** For creating the reviews table */
    public static final String CREATE_REVIEW_TABLE =
            "CREATE TABLE reviews (" +
                    "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                    "userId INTEGER NOT NULL, " +
                    "hotelId INTEGER NOT NULL, " +
                    "title varchar(128) NOT NULL, " +
                    "review TEXT NOT NULL, " +
                    "created DATETIME NOT NULL, " +
                    "modified DATETIME NOT NULL);";

    public static final String ADD_REVIEW_SQL =
            "INSERT INTO reviews (userId, hotelId, title, review, created, modified) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    public static final String SELECT_REVIEW_BY_REVIEW_ID_SQL =
            "SELECT id, userId, hotelId, title, review, created, modified FROM reviews " +
                    "WHERE id = ?";

    public static final String SELECT_REVIEWS_BY_HOTEL_ID_SQL =
            "SELECT id, userId, hotelId, title, review, created, modified FROM reviews " +
                    "WHERE hotelId = ?";

    public static final String DELETE_REVIEW_BY_REVIEW_ID_SQL =
            "DELETE FROM reviews WHERE id = ?";

    public static final String UPDATE_REVIEW_SQL =
            "UPDATE reviews SET title = ?, review = ?, modified = ? " +
                    "WHERE id = ?;";
}
