package edu.ib.networktechnologies.exceptions;

import edu.ib.networktechnologies.entities.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReviewAlreadyExistsException extends RuntimeException  {

    private ReviewAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String username) {
        ReviewAlreadyExistsException exception = new ReviewAlreadyExistsException("Review with username " + username + " already exists");
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }

}
