package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.controllers.dto.books.*;
import edu.ib.networktechnologies.entities.Book;
import edu.ib.networktechnologies.exceptions.BookNotFoundException;
import edu.ib.networktechnologies.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<GetBookDto> getAll() {
        var books = bookRepository.findAll();
        return books.stream().map(this::mapBook).toList();
    }

    private GetBookDto mapBook(Book book) {
        return new GetBookDto(book.getBookId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYearPublished(), book.getAvailableCopies() > 0);
    }

    public GetBookDto getOne(long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id));
        return mapBook(book);
    }

    public CreateBookResponseDto create(CreateBookDto book) {
        var bookEntity = new Book();
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setTitle(book.getTitle());
        bookEntity.setYearPublished(book.getYearPublished());
        bookEntity.setAvailableCopies(book.getAvailableCopies());

        var newBook = bookRepository.save(bookEntity);
        return new CreateBookResponseDto(newBook.getBookId(), newBook.getIsbn(), newBook.getTitle(), newBook.getAuthor(), newBook.getPublisher(), newBook.getYearPublished(), newBook.getAvailableCopies());
    }

    public void delete(long id) {
        if (!bookRepository.existsById(id)) throw BookNotFoundException.create(id);

        bookRepository.deleteById(id);
    }


    public CreateBookDetailsResponseDto createDetails(CreateBookDetailsDto detailsDto, long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        var details = new Book.BookDetails();
        details.setGenre(detailsDto.getGenre());
        details.setSummary(detailsDto.getSummary());
        details.setCoverImageURL(detailsDto.getCoverUrl());

        book.setBookDetails(details);
        bookRepository.save(book);

        return new CreateBookDetailsResponseDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYearPublished(), book.getAvailableCopies(), new BookDetailsDto(book.getBookDetails().getGenre(), book.getBookDetails().getSummary(), book.getBookDetails().getCoverImageURL()));
    }

    public UpdateBookDetailsResponseDto updateDetails(UpdateBookDetailsDto detailsDto, long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        var details = new Book.BookDetails();
        details.setGenre(detailsDto.getGenre());
        details.setSummary(detailsDto.getSummary());
        details.setCoverImageURL(detailsDto.getCoverUrl());

        book.setBookDetails(details);
        bookRepository.save(book);

        return new UpdateBookDetailsResponseDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYearPublished(), book.getAvailableCopies(), new BookDetailsDto(book.getBookDetails().getGenre(), book.getBookDetails().getSummary(), book.getBookDetails().getCoverImageURL()));
    }

    public GetBookDetailsDto getDetails(long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        return new GetBookDetailsDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYearPublished(), book.getAvailableCopies(), new BookDetailsDto(book.getBookDetails().getGenre(), book.getBookDetails().getSummary(), book.getBookDetails().getCoverImageURL()));
    }

}
