package edu.ib.networktechnologies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoanNotFoundException {
    public static ResponseStatusException create(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan with id " + id + " not found");
    }
}
