package edu.ib.networktechnologies.controllers.dto.review;

import java.sql.Date;

public class CreateReviewDto {

    private String rating;
    private String comment;
    private Date reviewDate;

    public CreateReviewDto(String rating, String comment, Date reviewDate) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
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

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}
