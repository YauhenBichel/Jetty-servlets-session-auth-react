package bichel.yauhen.hotel.api.repository;

import bichel.yauhen.hotel.api.model.Hotel;

import java.util.Optional;

/**
 * Interface for hotel repository
 */
public interface HotelRepository {
    Optional<Hotel> create(Hotel hotel);
    Optional<Hotel> get(Hotel hotel);
    Optional<Hotel> remove(Hotel hotel);
}
