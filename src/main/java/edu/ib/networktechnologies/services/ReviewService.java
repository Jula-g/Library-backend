package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.controllers.dto.review.CreateReviewDto;
import edu.ib.networktechnologies.controllers.dto.review.CreateReviewResponseDto;
import edu.ib.networktechnologies.controllers.dto.review.GetReviewDto;
import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.entities.Book;
import edu.ib.networktechnologies.entities.Review;
import edu.ib.networktechnologies.exceptions.BookNotFoundException;
import edu.ib.networktechnologies.repositories.BookRepository;
import edu.ib.networktechnologies.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    public List<GetReviewDto> getAll(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        List<GetReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : book.getReviews()) {
            GetUserDto userDto = new GetUserDto(
                    review.getUser().getUserId(),
                    review.getUser().getName(),
                    review.getUser().getEmail(),
                    review.getUser().getLastName()
            );
            GetReviewDto reviewDto = new GetReviewDto(
                    review.getRating(),
                    review.getComment(),
                    review.getReviewDate(),
                    userDto
            );
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }

    public GetReviewDto getOne(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        Review review = book.getReviews().get(0); // Assuming the first review
        GetUserDto userDto = new GetUserDto(
                review.getUser().getUserId(),
                review.getUser().getName(),
                review.getUser().getEmail(),
                review.getUser().getLastName()
        );
        return new GetReviewDto(
                review.getRating(),
                review.getComment(),
                review.getReviewDate(),
                userDto
        );
    }

    public CreateReviewResponseDto create(CreateReviewDto reviewDto, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> BookNotFoundException.create(bookId));
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setReviewDate(new Date(System.currentTimeMillis()));
        review.setBook(book);

        review = reviewRepository.save(review);
        return new CreateReviewResponseDto(
                review.getRating(),
                review.getComment(),
                review.getReviewDate().toString(),
                new GetUserDto(
                        review.getUser().getUserId(),
                        review.getUser().getName(),
                        review.getUser().getEmail(),
                        review.getUser().getLastName()
                ));
    }
}
