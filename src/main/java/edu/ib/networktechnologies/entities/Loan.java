package edu.ib.networktechnologies.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Basic
    @Column(name = "loan_date", nullable = false)
    private Date loanDate;

    @Basic
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Nullable
    @Basic
    @Column(name = "return_date")
    private Date returnDate;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Nullable
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(@Nullable Date returnDate) {
        this.returnDate = returnDate;
    }
}