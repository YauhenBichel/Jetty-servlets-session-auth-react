package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.vo.HotelResponse;
import bichel.yauhen.hotel.api.vo.ReviewResponse;
import bichel.yauhen.hotel.api.vo.WordHotelsResponse;
import bichel.yauhen.hotel.api.vo.WordReviewsResponse;
import bichel.yauhen.hotel.core.model.Hotel;
import bichel.yauhen.hotel.core.model.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Mapper for Reviews with a word
 */
public class WordHotelsMapper {

    private final HotelMapper hotelMapper;

    public WordHotelsMapper(HotelMapper hotelMapper) {
        this.hotelMapper = hotelMapper;
    }

    /**
     * Maps list of hotels to response WordReviewsResponse
     * @param word String
     * @param hotels List<Hotel>
     * @return WordReviewsResponse
     */
    public WordHotelsResponse mapToResponse(String word, Set<Hotel> hotels)  {
        WordHotelsResponse response = new WordHotelsResponse();

        response.setSuccess(true);
        response.setWord(word);

        List<HotelResponse> hotelResponses = new ArrayList<>();
        hotels.forEach(hotel -> hotelResponses.add(hotelMapper.mapToResponse(hotel)));
        response.setHotels(hotelResponses);

        return response;
    }
}
