package bichel.yauhen.hotel.api.vo;

/**
 * Hotel review request model
 */
public final class HotelReviewRequest {
    private String hotelId;
    private String title;
    private String review;

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelReviewRequest)) return false;

        HotelReviewRequest that = (HotelReviewRequest) o;

        if (hotelId != null ? !hotelId.equals(that.hotelId) : that.hotelId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return review != null ? review.equals(that.review) : that.review == null;
    }

    @Override
    public int hashCode() {
        int result = hotelId != null ? hotelId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
    }
}
