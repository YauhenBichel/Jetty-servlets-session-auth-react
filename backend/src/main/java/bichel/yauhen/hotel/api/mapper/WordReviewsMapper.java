package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.vo.ReviewResponse;
import bichel.yauhen.hotel.api.vo.WordReviewsResponse;
import bichel.yauhen.hotel.core.model.Review;

import java.util.List;
import java.util.ArrayList;

/**
 * Mapper for Reviews with a word
 */
public class WordReviewsMapper {

    private final ReviewMapper reviewMapper;

    public WordReviewsMapper(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    /**
     * Maps list of reviews to response WordReviewsResponse
     * @param word String
     * @param reviews List<Review>
     * @return WordReviewsResponse
     */
    public WordReviewsResponse mapToResponse(String word, List<Review> reviews)  {
        WordReviewsResponse response = new WordReviewsResponse();

        response.setSuccess(true);
        response.setWord(word);

        List<ReviewResponse> reviewResponses = new ArrayList<>();
        reviews.forEach(review -> reviewResponses.add(reviewMapper.mapToResponse(review)));
        response.setReviews(reviewResponses);

        return response;
    }
}
