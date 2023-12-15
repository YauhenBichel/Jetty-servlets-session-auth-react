package bichel.yauhen.hotel.api.service;

import bichel.yauhen.hotel.api.model.Hotel;
import bichel.yauhen.hotel.api.vo.HotelRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * Basic implementation of hotel service
 */
public class BasicHotelService implements HotelService {
    private static final Logger logger = LogManager.getLogger(BasicHotelService.class);


    public BasicHotelService() {
    }


    @Override
    public Hotel add(HotelRequest request) {
        return null;
    }

    @Override
    public Hotel get(int hotelId) {
        return null;
    }

    @Override
    public List<Hotel> getHotels() {
        return null;
    }
}
