package edu.ib.networktechnologies.controllers.dto.books;

public class UpdateBookDto {

        private String isbn;

        private String title;

        private String author;

        private String publisher;

        private int yearPublished;

        private int availableCopies;

        private BookDetailsDto bookDetails;

        public UpdateBookDto(String isbn, String title, String author, String publisher, int yearPublished, int availableCopies, BookDetailsDto bookDetails) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.yearPublished = yearPublished;
            this.availableCopies = availableCopies;
            this.bookDetails = bookDetails;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getPublisher() {
            return publisher;
        }

        public int getYearPublished() {
            return yearPublished;
        }

        public int getAvailableCopies() {
            return availableCopies;
        }

    public BookDetailsDto getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetailsDto bookDetails) {
        this.bookDetails = bookDetails;
    }
}
