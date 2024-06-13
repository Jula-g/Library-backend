package edu.ib.networktechnologies.controllers.dto.loans;

import jakarta.validation.constraints.NotNull;

public class CreateLoanDto {

    @NotNull
    private String dueDate;

    @NotNull
    private Long bookId;

    public CreateLoanDto( String dueDate, long bookId) {
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }
}
