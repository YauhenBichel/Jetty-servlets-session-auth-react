package bichel.yauhen.hotel.api.vo;

import java.util.List;

/**
 * Response model for hotels with some word
 */
public final class WordHotelsResponse {
    private boolean success;
    private String word;
    private List<HotelResponse> hotels;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<HotelResponse> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelResponse> hotels) {
        this.hotels = hotels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordHotelsResponse)) return false;

        WordHotelsResponse that = (WordHotelsResponse) o;

        if (success != that.success) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        return hotels != null ? hotels.equals(that.hotels) : that.hotels == null;
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + (hotels != null ? hotels.hashCode() : 0);
        return result;
    }
}
