package edu.ib.networktechnologies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException {
    public static ResponseStatusException createWithId(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
    }

    public static ResponseStatusException createWithUsername(String username) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " not found");
    }
}