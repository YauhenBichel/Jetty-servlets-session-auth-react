package bichel.yauhen.hotel.api.repository;

import bichel.yauhen.hotel.api.model.ReviewEntity;

import java.util.Optional;
import java.util.List;

/**
 * Interface for review repository
 */
public interface ReviewRepository {
    Optional<ReviewEntity> create(ReviewEntity review);
    Optional<ReviewEntity> get(int reviewId);
    Optional<ReviewEntity> update(ReviewEntity review);
    List<ReviewEntity> getByHotelId(int hotelId);
    int delete(int reviewId);
}
