package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.controllers.dto.books.BookDetailsDto;
import edu.ib.networktechnologies.controllers.dto.books.GetBookDetailsDto;
import edu.ib.networktechnologies.controllers.dto.review.CreateReviewDto;
import edu.ib.networktechnologies.controllers.dto.review.GetReviewDto;
import edu.ib.networktechnologies.controllers.dto.review.UpdateReviewDto;
import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.entities.Auth;
import edu.ib.networktechnologies.entities.Book;
import edu.ib.networktechnologies.entities.Review;
import edu.ib.networktechnologies.entities.User;
import edu.ib.networktechnologies.exceptions.BookNotFoundException;
import edu.ib.networktechnologies.exceptions.UserNotFoundException;
import edu.ib.networktechnologies.repositories.AuthRepository;
import edu.ib.networktechnologies.repositories.BookRepository;
import edu.ib.networktechnologies.repositories.ReviewRepository;
import edu.ib.networktechnologies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthRepository authRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository, AuthRepository authRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public List<GetReviewDto> getAll(long bookId) {
        List<Review> reviews = reviewRepository.findAllByBookBookId(bookId);
        List<GetReviewDto> reviewList = new ArrayList<>();

        for (Review review : reviews) {
            reviewList.add(mapReview(review));
        }

        return reviewList;
    }

    private GetReviewDto mapReview(Review review) {
        return new GetReviewDto(
                review.getReviewId(),
                new GetBookDetailsDto(
                        review.getBook().getBookId(),
                        review.getBook().getIsbn(),
                        review.getBook().getTitle(),
                        review.getBook().getAuthor(),
                        review.getBook().getPublisher(),
                        review.getBook().getYearPublished(),
                        review.getBook().getAvailableCopies(),
                        new BookDetailsDto(
                                review.getBook().getBookDetails().getGenre(),
                                review.getBook().getBookDetails().getSummary(),
                                review.getBook().getBookDetails().getCoverImageURL()
                        )
                ),
                review.getRating(),
                review.getComment(),
                review.getReviewDate(),
                new GetUserDto(
                        review.getUser().getUserId(),
                        review.getUser().getName(),
                        review.getUser().getLastName(),
                        review.getUser().getEmail()
                )
        );
    }

    private boolean findOneForUserAndBook(long bookId, long userId) {
           Optional<Review> review = reviewRepository.findByBookBookIdAndUserUserId(bookId, userId);
           return review.isPresent();
    }


    public GetReviewDto create(CreateReviewDto reviewDto, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        User user = userRepository.findById(reviewDto.getUserId()).orElseThrow(() -> UserNotFoundException.createWithId(reviewDto.getUserId()));

        if (findOneForUserAndBook(bookId, reviewDto.getUserId())) {
           Optional<Auth> auth = authRepository.findByUserUserId(reviewDto.getUserId());
           if (auth.isPresent()) {
               throw UserNotFoundException.createWithUsername(auth.get().getUsername());
           }
        }

        Review review = new Review();
        if (Integer.parseInt(reviewDto.getRating()) > 5) {
            review.setRating(5 + "/5");
        }else{
            review.setRating(reviewDto.getRating() + "/5");
        }
        review.setComment(reviewDto.getComment());
        review.setReviewDate(new Date(System.currentTimeMillis()));
        review.setBook(book);
        review.setUser(user);

        review = reviewRepository.save(review);
        return mapReview(review);
    }

    public void delete(long id) {
        reviewRepository.deleteById(id);
    }

    public GetReviewDto update(UpdateReviewDto reviewDto, long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id));
        if (Integer.parseInt(reviewDto.getRating()) > 5) {
            review.setRating(5 + "/5");
        }else{
            review.setRating(reviewDto.getRating() + "/5");
        }
        review.setComment(reviewDto.getComment());
        review.setReviewDate(new Date(System.currentTimeMillis()));
        review = reviewRepository.save(review);
        return mapReview(review);
    }
}

