package bichel.yauhen.hotel.api.service;

import bichel.yauhen.hotel.api.model.Hotel;
import bichel.yauhen.hotel.api.vo.HotelRequest;

import java.util.List;

/**
 * Interface for hotel service
 */
public interface HotelService {
    Hotel add(HotelRequest request);
    Hotel get(int hotelId);
    List<Hotel> getHotels();
}
