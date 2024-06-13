package edu.ib.networktechnologies.controllers.dto.review;

public class UpdateReviewDto {

    private String rating;
    private String comment;

    public UpdateReviewDto(String rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
