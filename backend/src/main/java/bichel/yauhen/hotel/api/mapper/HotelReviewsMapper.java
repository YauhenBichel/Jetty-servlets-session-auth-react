package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.vo.HotelReviewResponse;
import bichel.yauhen.hotel.api.vo.HotelReviewsResponse;
import bichel.yauhen.hotel.api.vo.ReviewsResponse;
import bichel.yauhen.hotel.api.vo.ReviewResponse;
import bichel.yauhen.hotel.core.model.Review;

import java.util.List;
import java.util.ArrayList;

/**
 * Mapper for Reviews of hotel
 */
public class HotelReviewsMapper {
    private final HotelReviewMapper hotelReviewMapper;

    public HotelReviewsMapper(HotelReviewMapper hotelReviewMapper) {
        this.hotelReviewMapper = hotelReviewMapper;
    }

    /**
     * Maps list of reviews to response HotelReviewsResponse
     * @param hotelId String
     * @param reviews List<ReviewEntity>
     * @return HotelReviewsResponse
     */
    public HotelReviewsResponse mapToResponse(String hotelId, List<ReviewEntity> reviews) {
        HotelReviewsResponse response = new HotelReviewsResponse();

        response.setSuccess(true);
        response.setHotelId(hotelId);

        List<HotelReviewResponse> reviewResponses = new ArrayList<>();
        reviews.forEach(review -> reviewResponses.add(hotelReviewMapper.mapToResponse(review)));

        response.setReviews(reviewResponses);

        return response;
    }
}
