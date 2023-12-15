package bichel.yauhen.hotel.api.model;

public class ReviewEntity extends Audit {
    private int id;
    private int userId;
    private int hotelId;
    private String title;
    private String review;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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
        if (!(o instanceof ReviewEntity)) return false;

        ReviewEntity review1 = (ReviewEntity) o;

        if (id != review1.id) return false;
        if (userId != review1.userId) return false;
        if (hotelId != review1.hotelId) return false;
        if (title != null ? !title.equals(review1.title) : review1.title != null) return false;
        return review != null ? review.equals(review1.review) : review1.review == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + hotelId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
    }
}
