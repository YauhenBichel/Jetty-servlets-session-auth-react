package bichel.yauhen.hotel.api.service;

import bichel.yauhen.hotel.api.mapper.HotelReviewMapper;
import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.repository.ReviewRepository;
import bichel.yauhen.hotel.api.vo.HotelReviewRequest;
import bichel.yauhen.hotel.api.vo.UpdateHotelReviewRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;


/**
 * Basic implementation of review service
 */
public class BasicReviewService implements ReviewService {
    private static final Logger logger = LogManager.getLogger(BasicReviewService.class);
    private final ReviewRepository reviewRepository;
    private final HotelReviewMapper hotelReviewMapper;

    public BasicReviewService(ReviewRepository reviewRepository, HotelReviewMapper hotelReviewMapper) {
        this.reviewRepository = reviewRepository;
        this.hotelReviewMapper = hotelReviewMapper;
    }

    @Override
    public ReviewEntity add(int userId, HotelReviewRequest request) {
        logger.info("Add a review");

        ReviewEntity reviewEntity = hotelReviewMapper.mapToEntity(userId, request);

        Optional<ReviewEntity> dbReviewOpt = reviewRepository.create(reviewEntity);

        if(dbReviewOpt.isEmpty()) {
            logger.error("review is not added for " + reviewEntity.getTitle());
            throw new RuntimeException("review is not added for " + reviewEntity.getTitle());
        }

        return dbReviewOpt.get();
    }

    @Override
    public ReviewEntity update(int reviewId, int userId, UpdateHotelReviewRequest request) {
        logger.info("Update review");

        ReviewEntity reviewEntity = hotelReviewMapper.mapToEntityFromUpdate(userId, request);
        reviewEntity.setId(reviewId);

        Optional<ReviewEntity> dbReviewOpt = reviewRepository.update(reviewEntity);

        if(dbReviewOpt.isEmpty()) {
            logger.error("review is not updated for " + reviewEntity.getId());
            throw new RuntimeException("review is not updated for " + reviewEntity.getId());
        }

        return dbReviewOpt.get();
    }

    @Override
    public ReviewEntity get(int reviewId) {
        return null;
    }

    @Override
    public List<ReviewEntity> getByHotelId(int hotelId) {
        List<ReviewEntity> reviews = reviewRepository.getByHotelId(hotelId);
        return reviews;
    }

    @Override
    public void delete(int reviewId) {
        reviewRepository.delete(reviewId);
    }
}
