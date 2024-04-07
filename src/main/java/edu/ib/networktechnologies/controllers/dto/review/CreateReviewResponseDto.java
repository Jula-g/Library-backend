package edu.ib.networktechnologies.controllers.dto.review;

import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;

public class CreateReviewResponseDto {

    private String rating;
    private String comment;
    private String reviewDate;
    private GetUserDto user;

    public CreateReviewResponseDto(String rating, String comment, String reviewDate, GetUserDto user) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.user = user;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setUser(GetUserDto user) {
        this.user = user;
    }

    public String getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public GetUserDto getUser() {
        return user;
    }
}

