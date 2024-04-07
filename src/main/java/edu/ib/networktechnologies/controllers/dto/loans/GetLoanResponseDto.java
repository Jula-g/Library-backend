package edu.ib.networktechnologies.controllers.dto.loans;

import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.controllers.dto.books.GetBookDto;

import java.sql.Date;

public class GetLoanResponseDto {

    private long id;
    private Date loanDate;
    private Date dueDate;
    private GetUserDto userId;
    private GetBookDto bookId;

    public GetLoanResponseDto() {
    }

    public GetLoanResponseDto(long id, Date loanDate, Date dueDate, GetUserDto userId, GetBookDto bookId) {
        this.id = id;
        this.loanDate = loanDate;
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

    public void setUserId(GetUserDto userId) {
        this.userId = userId;
    }

    public void setBookId(GetBookDto bookId) {
        this.bookId = bookId;
    }

    public GetUserDto getUserId() {
        return userId;
    }

    public GetBookDto getBookId() {
        return bookId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }
}
