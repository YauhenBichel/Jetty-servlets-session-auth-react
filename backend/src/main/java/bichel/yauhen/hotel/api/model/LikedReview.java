package bichel.yauhen.hotel.api.model;

public class LikedReview extends Audit {
    private int reviewId;
    private int userId;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
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
        if (!(o instanceof LikedReview)) return false;

        LikedReview that = (LikedReview) o;

        if (reviewId != that.reviewId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = reviewId;
        result = 31 * result + userId;
        return result;
    }
}
