package edu.ib.networktechnologies.controllers.dto.loans;

import edu.ib.networktechnologies.controllers.dto.books.GetBookDetailsDto;
import jakarta.validation.constraints.NotNull;

public class UpdateLoanDto {

    @NotNull
    private long loanId;

    @NotNull
    private long bookId;

    public UpdateLoanDto(long loanId, long bookId) {
        this.loanId = loanId;
        this.bookId = bookId;
    }

    @NotNull
    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(@NotNull long loanId) {
        this.loanId = loanId;
    }

    public @NotNull long getBookId() {
        return bookId;
    }

    public void setBookId(@NotNull long bookId) {
        this.bookId = bookId;
    }
}
