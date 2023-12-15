package bichel.yauhen.hotel.api.vo;

/**
 * Hotel review request model
 */
public final class UpdateHotelReviewRequest {
    private String title;
    private String review;

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
        if (!(o instanceof UpdateHotelReviewRequest)) return false;

        UpdateHotelReviewRequest that = (UpdateHotelReviewRequest) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return review != null ? review.equals(that.review) : that.review == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
    }
}
