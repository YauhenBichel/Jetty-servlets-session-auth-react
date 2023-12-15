package bichel.yauhen.hotel.api.service;

import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.vo.HotelReviewRequest;
import bichel.yauhen.hotel.api.vo.UpdateHotelReviewRequest;

import java.util.List;

/**
 * Interface for review service
 */
public interface ReviewService {
    ReviewEntity add(int userId, HotelReviewRequest request);
    ReviewEntity update(int reviewId, int userId, UpdateHotelReviewRequest request);
    ReviewEntity get(int reviewId);
    List<ReviewEntity> getByHotelId(int hotelId);
    void delete(int reviewId);
}
