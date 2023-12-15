package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.model.Account;
import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.vo.HotelReviewRequest;
import bichel.yauhen.hotel.api.vo.RegistrationRequest;
import bichel.yauhen.hotel.api.vo.ReviewResponse;
import bichel.yauhen.hotel.core.model.Review;

import java.time.LocalDateTime;

/**
 * Mapper for Review
 */
public class ReviewMapper {
    /**
     * Maps model Review to response ReviewResponse
     * @param model Review
     * @return ReviewResponse
     */
    public ReviewResponse mapToResponse(Review model) {
        ReviewResponse response = new ReviewResponse();

        response.setReviewId(model.getId());
        response.setReviewText(model.getReviewText());
        response.setTitle(model.getTitle());
        String user = model.getUserNickname().isEmpty() ? "Anonymous" : model.getUserNickname();
        response.setUser(user);
        response.setDate(model.getReviewSubmissionTime().toString());

        return response;
    }
}
