package bichel.yauhen.hotel.api.model;

public class FavoriteHotel extends Audit {
    private int hotelId;
    private int userId;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteHotel)) return false;

        FavoriteHotel that = (FavoriteHotel) o;

        if (hotelId != that.hotelId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = hotelId;
        result = 31 * result + userId;
        return result;
    }
}
