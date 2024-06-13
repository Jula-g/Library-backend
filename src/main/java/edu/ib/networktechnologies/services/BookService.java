package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.controllers.dto.books.*;
import edu.ib.networktechnologies.entities.Book;
import edu.ib.networktechnologies.exceptions.BookNotFoundException;
import edu.ib.networktechnologies.repositories.BookRepository;
import edu.ib.networktechnologies.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public BookService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public List<GetBookDetailsDto> getAll() {
        var books = bookRepository.findAll();
        return books.stream().map(this::mapBook).toList();
    }

    private GetBookDetailsDto mapBook(Book book) {
        return new GetBookDetailsDto(
                book.getBookId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getAvailableCopies(),
                new BookDetailsDto(
                        book.getBookDetails().getGenre(),
                        book.getBookDetails().getSummary(),
                        book.getBookDetails().getCoverImageURL()
                )
        );
    }

    public GetBookDetailsDto getOne(long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id));
        return mapBook(book);
    }

    public CreateBookDetailsResponseDto create(CreateBookDto book) {
        var newBook = new Book();
        newBook.setAuthor(book.getAuthor());
        newBook.setIsbn(book.getIsbn());
        newBook.setPublisher(book.getPublisher());
        newBook.setTitle(book.getTitle());
        newBook.setYearPublished(book.getYearPublished());
        newBook.setAvailableCopies(book.getAvailableCopies());
        var bookDetails = new Book.BookDetails();
        bookDetails.setGenre("");
        bookDetails.setSummary("");
        bookDetails.setCoverImageURL("");
        newBook.setBookDetails(bookDetails);

        var savedBook = bookRepository.save(newBook);
        return new CreateBookDetailsResponseDto(
                savedBook.getBookId(),
                savedBook.getIsbn(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getPublisher(),
                savedBook.getYearPublished(),
                savedBook.getAvailableCopies(),
                new BookDetailsDto(
                        savedBook.getBookDetails().getGenre(),
                        savedBook.getBookDetails().getSummary(),
                        savedBook.getBookDetails().getCoverImageURL()
                )
        );
    }

    public CreateBookDetailsResponseDto update(UpdateBookDto book, long id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id));
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setYearPublished(book.getYearPublished());
        bookEntity.setAvailableCopies(book.getAvailableCopies());

        var bookDetails = bookEntity.getBookDetails();
        bookDetails.setGenre(book.getBookDetails().getGenre());
        bookDetails.setSummary(book.getBookDetails().getSummary());
        bookDetails.setCoverImageURL(book.getBookDetails().getCoverUrl());
        bookEntity.setBookDetails(bookDetails);

        var savedBook = bookRepository.save(bookEntity);
        return new CreateBookDetailsResponseDto(
                savedBook.getBookId(),
                savedBook.getIsbn(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getPublisher(),
                savedBook.getYearPublished(),
                savedBook.getAvailableCopies(),
                new BookDetailsDto(
                        savedBook.getBookDetails().getGenre(),
                        savedBook.getBookDetails().getSummary(),
                        savedBook.getBookDetails().getCoverImageURL()
                )
        );
    }

    public void delete(long id) {
        if (!bookRepository.existsById(id)) throw BookNotFoundException.create(id);
        if (loanRepository.existsByBook_BookId(id)) throw new RuntimeException("Book is borrowed");
        bookRepository.deleteById(id);
    }

    public GetBookDetailsDto getDetails(long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        return new GetBookDetailsDto(
                book.getBookId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getYearPublished(),
                book.getAvailableCopies(),
                new BookDetailsDto(
                        book.getBookDetails().getGenre(),
                        book.getBookDetails().getSummary(),
                        book.getBookDetails().getCoverImageURL()
                )
        );
    }

    public GetBookDetailsDto getBookByTitle(String title) {
        if (title.contains("%20")) {
            title = title.replace("%20", " ");
        }

        String finalTitle = title;
        var book = bookRepository.findByTitle(title).orElseThrow(() -> BookNotFoundException.create(finalTitle));
        return mapBook(book);
    }
}
