package edu.ib.networktechnologies.controllers.dto.loans;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public class CreateLoanDto {

    @NotNull
    private Date dueDate;

    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;

    public CreateLoanDto() {
    }

    public CreateLoanDto( Date dueDate, long userId, long bookId) {
        this.dueDate = dueDate;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getUserId() {
        return userId;
    }

    public long getBookId() {
        return bookId;
    }
}
