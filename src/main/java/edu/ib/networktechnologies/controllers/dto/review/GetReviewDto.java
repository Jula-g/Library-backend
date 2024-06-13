package edu.ib.networktechnologies.controllers.dto.review;

import edu.ib.networktechnologies.controllers.dto.books.GetBookDetailsDto;
import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;

import java.sql.Date;

public class GetReviewDto {

    private long reviewId;
    private GetBookDetailsDto book;
    private String rating;
    private String comment;
    private Date reviewDate;
    private GetUserDto user;

    public GetReviewDto(long reviewId, GetBookDetailsDto book, String rating, String comment, Date reviewDate, GetUserDto user) {
        this.reviewId = reviewId;
        this.book = book;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.user = user;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public GetBookDetailsDto getBook() {
        return book;
    }

    public void setBook(GetBookDetailsDto book) {
        this.book = book;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(Date reviewDate) {
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

    public Date getReviewDate() {
        return reviewDate;
    }

    public GetUserDto getUser() {
        return user;
    }
}
