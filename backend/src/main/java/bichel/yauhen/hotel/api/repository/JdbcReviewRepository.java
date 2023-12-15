package bichel.yauhen.hotel.api.repository;

import bichel.yauhen.hotel.api.db.SqlConnectionPool;
import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.utils.DateTimeFormatterUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Jdbc implementation of review repository
 */
public class JdbcReviewRepository implements ReviewRepository {

    private static final Logger logger = LogManager.getLogger(JdbcReviewRepository.class);
    private final SqlConnectionPool sqlConnectionPool;

    public JdbcReviewRepository(SqlConnectionPool sqlConnectionPool) {
        this.sqlConnectionPool = sqlConnectionPool;
    }

    /**
     * Adds a new review
     *
     * @param review - Review
     */
    @Override
    public Optional<ReviewEntity> create(ReviewEntity review) {
        ReviewEntity dbReview = new ReviewEntity();

        PreparedStatement statement = null;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Review: dbConnection successful");
            try {
                statement = connection.prepareStatement(ReviewPreparedStatements.ADD_REVIEW_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, review.getUserId());
                statement.setInt(2, review.getHotelId());
                statement.setString(3, review.getTitle());
                statement.setString(4, review.getReview());
                statement.setString(5, review.getCreated().toString());
                statement.setString(6, review.getModified().toString());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        dbReview.setId(generatedKeys.getInt(1));
                        dbReview.setUserId(review.getUserId());
                        dbReview.setHotelId(review.getHotelId());
                        dbReview.setTitle(review.getTitle());
                        dbReview.setReview(review.getReview());
                        dbReview.setCreated(review.getCreated());
                        dbReview.setModified(review.getModified());
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

        return Optional.of(dbReview);
    }

    public Optional<ReviewEntity> get(int reviewId) {
        PreparedStatement statement;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Review: dbConnection successful");
            statement = connection.prepareStatement(ReviewPreparedStatements.SELECT_REVIEW_BY_REVIEW_ID_SQL);

            statement.setInt(1, reviewId);
            ResultSet resultSet = statement.executeQuery();
            ReviewEntity dbReview = null;
            while(resultSet.next()) {
                dbReview = new ReviewEntity();
                dbReview.setId(resultSet.getInt("id"));
                dbReview.setUserId(resultSet.getInt("userId"));
                dbReview.setHotelId(resultSet.getInt("hotelId"));
                dbReview.setTitle(resultSet.getString("title"));
                dbReview.setReview(resultSet.getString("review"));
                dbReview.setCreated(LocalDateTime.parse(resultSet.getString("created"), DateTimeFormatterUtils.toDateTimeFormatter()));
                dbReview.setModified(LocalDateTime.parse(resultSet.getString("modified"), DateTimeFormatterUtils.toDateTimeFormatter()));
            }

            if(!statement.isClosed()) {
                statement.close();
            }

            return dbReview == null ? Optional.empty() : Optional.of(dbReview);
        } catch (SQLException ex) {
            logger.error(ex);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ReviewEntity> update(ReviewEntity review) {
        ReviewEntity dbReview = new ReviewEntity();

        PreparedStatement statement = null;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Review: dbConnection successful");
            try {
                statement = connection.prepareStatement(ReviewPreparedStatements.UPDATE_REVIEW_SQL,
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, review.getTitle());
                statement.setString(2, review.getReview());
                statement.setString(3, review.getModified().toString());
                statement.setInt(4, review.getId());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("User updating failed, no rows affected.");
                }

                dbReview = get(review.getId()).get();

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

        return Optional.of(dbReview);
    }

    @Override
    public List<ReviewEntity> getByHotelId(int hotelId) {
        List<ReviewEntity> reviews = new ArrayList<>();

        PreparedStatement statement;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Review: dbConnection successful");
            statement = connection.prepareStatement(ReviewPreparedStatements.SELECT_REVIEWS_BY_HOTEL_ID_SQL);

            statement.setInt(1, hotelId);
            ResultSet resultSet = statement.executeQuery();
            ReviewEntity dbReview = null;
            while(resultSet.next()) {
                dbReview = new ReviewEntity();
                dbReview.setId(resultSet.getInt("id"));
                dbReview.setUserId(resultSet.getInt("userId"));
                dbReview.setHotelId(resultSet.getInt("hotelId"));
                dbReview.setTitle(resultSet.getString("title"));
                dbReview.setReview(resultSet.getString("review"));
                dbReview.setCreated(LocalDateTime.parse(resultSet.getString("created"), DateTimeFormatterUtils.toDateTimeFormatter()));
                dbReview.setModified(LocalDateTime.parse(resultSet.getString("modified"), DateTimeFormatterUtils.toDateTimeFormatter()));

                reviews.add(dbReview);
            }

            if(!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }

        return reviews;
    }

    @Override
    public int delete(int reviewId) {
        int affectedRows = 0;
        PreparedStatement statement;
        try (Connection connection = sqlConnectionPool.getConnection()) {
            logger.info("Review: dbConnection successful");
            statement = connection.prepareStatement(ReviewPreparedStatements.DELETE_REVIEW_BY_REVIEW_ID_SQL);
            statement.setInt(1, reviewId);
            affectedRows = statement.executeUpdate();

            if(!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException ex) {
            logger.error(ex);
        }

        return affectedRows;
    }
}
