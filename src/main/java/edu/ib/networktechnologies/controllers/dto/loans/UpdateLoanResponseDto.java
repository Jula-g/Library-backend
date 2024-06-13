package edu.ib.networktechnologies.controllers.dto.loans;

import java.sql.Date;

public class UpdateLoanResponseDto {

    private Date loanDate;
    private Date dueDate;
    private long userId;
    private long bookId;
private Date returnDate;

    public UpdateLoanResponseDto(Date loanDate, Date dueDate, long userId, long bookId, Date returnDate) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.userId = userId;
        this.bookId = bookId;
        this.returnDate = returnDate;
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


    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
