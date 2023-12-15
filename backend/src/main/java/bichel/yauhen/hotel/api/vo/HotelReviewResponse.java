package bichel.yauhen.hotel.api.vo;

/**
 * Hotel review response model
 */
public final class HotelReviewResponse {
    private int id;
    private int hotelId;
    private int userId;
    private String title;
    private String review;
    private String created;
    private String modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelReviewResponse)) return false;

        HotelReviewResponse response = (HotelReviewResponse) o;

        if (id != response.id) return false;
        if (hotelId != response.hotelId) return false;
        if (userId != response.userId) return false;
        if (title != null ? !title.equals(response.title) : response.title != null) return false;
        if (review != null ? !review.equals(response.review) : response.review != null) return false;
        if (created != null ? !created.equals(response.created) : response.created != null) return false;
        return modified != null ? modified.equals(response.modified) : response.modified == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + hotelId;
        result = 31 * result + userId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
