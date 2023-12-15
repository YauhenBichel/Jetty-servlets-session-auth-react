package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.model.ReviewEntity;
import bichel.yauhen.hotel.api.vo.HotelReviewRequest;
import bichel.yauhen.hotel.api.vo.HotelReviewResponse;
import bichel.yauhen.hotel.api.vo.UpdateHotelReviewRequest;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Mapper for Hotel Reviews
 */
public class HotelReviewMapper {
    public HotelReviewRequest mapToHotelReviewRequest(HttpServletRequest servletRequest) throws IOException {
        Gson gson = new Gson();
        String body = servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return gson.fromJson(body, HotelReviewRequest.class);
    }

    public UpdateHotelReviewRequest mapToUpdateHotelReviewRequest(HttpServletRequest servletRequest) throws IOException {
        Gson gson = new Gson();
        String body = servletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return gson.fromJson(body, UpdateHotelReviewRequest.class);
    }

    public ReviewEntity mapToEntity(int userId, HotelReviewRequest request) {
        ReviewEntity entity = new ReviewEntity();
        entity.setTitle(request.getTitle());
        entity.setReview(request.getReview());
        entity.setHotelId(Integer.parseInt(request.getHotelId()));
        entity.setUserId(userId);
        entity.setCreated(LocalDateTime.now());
        entity.setModified(entity.getCreated());

        return entity;
    }

    public ReviewEntity mapToEntityFromUpdate(int userId, UpdateHotelReviewRequest request) {
        ReviewEntity entity = new ReviewEntity();
        entity.setTitle(request.getTitle());
        entity.setReview(request.getReview());
        entity.setUserId(userId);
        entity.setCreated(LocalDateTime.now());
        entity.setModified(entity.getCreated());

        return entity;
    }

    public HotelReviewResponse mapToResponse(ReviewEntity entity) {
        HotelReviewResponse response = new HotelReviewResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUserId());
        response.setHotelId(entity.getHotelId());
        response.setTitle(entity.getTitle());
        response.setReview(entity.getReview());
        response.setCreated(entity.getCreated().toString());
        response.setModified(entity.getModified().toString());

        return response;
    }
}
