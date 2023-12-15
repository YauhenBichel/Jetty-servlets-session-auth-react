package bichel.yauhen.hotel.core.data;

import bichel.yauhen.hotel.core.model.Hotel;
import bichel.yauhen.hotel.core.model.Review;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface methods for reading for hotels and their reviews
 */
public interface ThreadSafeHotelReviewDataForReading {
    /**
     * Gets information about the hotel with the given id
     * @param hotelId String value
     * @return Optional of Hotel
     */
    Optional<Hotel> findHotel(String hotelId);
    /**
     * Finds hotels with required word and return first amount items
     * @param word String value
     * @return list of Hotel
     */
    Set<Hotel> findHotelsByWord(String word, int amount);
    /**
     * Finds reviews using hotel id and return first amount items
     * @param hotelId int
     * @param amount int
     * @return list of reviews
     */
    List<Review> findReviews(int hotelId, int amount);
    /**
     * Finds reviews with required word and return first amount items
     * @param word String
     * @param amount int
     * @return list of reviews
     */
    List<Review> findReviewsByWord(String word, int amount);
}
