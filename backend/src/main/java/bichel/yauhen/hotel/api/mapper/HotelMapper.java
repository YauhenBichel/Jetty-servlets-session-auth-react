package bichel.yauhen.hotel.api.mapper;

import bichel.yauhen.hotel.api.vo.HotelResponse;
import bichel.yauhen.hotel.core.model.Hotel;

/**
 * Mapper for Hotel
 */
public class HotelMapper {
    /**
     * Maps model Hotel to response HotelResponse
     * @param model Hotel
     * @return HotelResponse
     */
    public HotelResponse mapToResponse(Hotel model) {
        HotelResponse response = new HotelResponse();
        response.setHotelId(String.valueOf(model.getId()));
        response.setName(model.getName());
        response.setAddr(model.getAddress());
        response.setCity(model.getCity());
        response.setState(model.getState());
        response.setLat(model.getLocation().getLatitude());
        response.setLng(model.getLocation().getLongitude());
        response.setSuccess(true);

        return response;
    }
}
