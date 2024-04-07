package edu.ib.networktechnologies.exceptions;

import org.springframework.web.server.ResponseStatusException;

public class BookDetailsNotFound {
    public static ResponseStatusException create(Long id) {
        return new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Book details with id " + id + " not found");
    }
}
