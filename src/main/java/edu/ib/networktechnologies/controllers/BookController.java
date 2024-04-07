package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.books.*;
import edu.ib.networktechnologies.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@PreAuthorize("hasRole('ADMIN')")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;

    }
    @GetMapping("/getAll")
    @PreAuthorize("isAuthenticated()")
    public List<GetBookDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetBookDto> getOne(@PathVariable long id) {
        GetBookDto dto = bookService.getOne(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<CreateBookResponseDto> create(@RequestBody CreateBookDto book) {
        var newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PatchMapping("/{bookId}/details")
    public ResponseEntity<UpdateBookDetailsResponseDto> updateDetails(@RequestBody UpdateBookDetailsDto detailsDto, @PathVariable long bookId) {
        var newDetails = bookService.updateDetails(detailsDto, bookId);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }

    @PostMapping("/{bookId}/details")
    public ResponseEntity<CreateBookDetailsResponseDto> createDetails(@RequestBody CreateBookDetailsDto detailsDto, @PathVariable long bookId) {
        var newDetails = bookService.createDetails(detailsDto, bookId);
        return new ResponseEntity<>(newDetails, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/details")
    public ResponseEntity<GetBookDetailsDto> getDetails(@PathVariable long bookId) {
        var details = bookService.getDetails(bookId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
