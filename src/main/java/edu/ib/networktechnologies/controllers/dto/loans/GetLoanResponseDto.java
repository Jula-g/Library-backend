package edu.ib.networktechnologies.controllers.dto.loans;

import edu.ib.networktechnologies.controllers.dto.books.GetBookDetailsDto;
import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;

import java.sql.Date;

public class GetLoanResponseDto {

    private long loanId;
    private Date loanDate;
    private Date dueDate;
    private GetUserDto user;
    private GetBookDetailsDto book;
    private Date returnDate;

    public GetLoanResponseDto(long loanId, Date loanDate, Date dueDate, GetUserDto user, GetBookDetailsDto book, Date returnDate) {
        this.loanId = loanId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.user = user;
        this.book = book;
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setUser(GetUserDto user) {
        this.user = user;
    }

    public void setBook(GetBookDetailsDto book) {
        this.book = book;
    }

    public GetUserDto getUser() {
        return user;
    }

    public GetBookDetailsDto getBook() {
        return book;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setId(long loanId) {
        this.loanId = loanId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
