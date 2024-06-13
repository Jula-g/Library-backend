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
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;

    }
    @GetMapping("/getAll")
    public List<GetBookDetailsDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GetBookDetailsDto> getOne(@PathVariable long id) {
        GetBookDetailsDto dto = bookService.getOne(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<CreateBookDetailsResponseDto> create(@RequestBody CreateBookDto book) {
        var newBook = bookService.create(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CreateBookDetailsResponseDto> update(@RequestBody UpdateBookDto book, @PathVariable long id) {
        var newBook = bookService.update(book, id);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/details")
    public ResponseEntity<GetBookDetailsDto> getDetails(@PathVariable long bookId) {
        var details = bookService.getDetails(bookId);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @GetMapping("/getBookByTitle/{title}")
    public ResponseEntity<GetBookDetailsDto> getBookByTitle(@PathVariable String title) {
        System.out.println("title: " + title);
        var book = bookService.getBookByTitle(title);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
