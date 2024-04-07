package edu.ib.networktechnologies.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long bookId;

    @Basic
    @Column(unique = true, name = "isbn")
    private String isbn;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "author")
    private String author;

    @Basic
    @Column(name = "publisher")
    private String publisher;

    @Basic
    @Column(name = "year_published")
    private int yearPublished;

    @Basic
    @Column(name = "available_copies")
    private int availableCopies;

    @OneToMany
    private List<Review> reviews;

    @Embedded
    private BookDetails bookDetails;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Loan> loans;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;

    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    @Embeddable
    public static class BookDetails {

        @Basic
        @Column(name = "genre")
        private String genre;

        @Basic
        @Column(name = "summary")
        private String summary;

        @Basic
        @Column(name = "cover_image_url")
        private String coverImageURL;

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getCoverImageURL() {
            return coverImageURL;
        }

        public void setCoverImageURL(String coverImageURL) {
            this.coverImageURL = coverImageURL;
        }
    }
}