package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.review.CreateReviewDto;
import edu.ib.networktechnologies.controllers.dto.review.GetReviewDto;
import edu.ib.networktechnologies.controllers.dto.review.UpdateReviewDto;
import edu.ib.networktechnologies.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create/{bookId}")
    public ResponseEntity<GetReviewDto> create(@RequestBody CreateReviewDto reviewDto, @PathVariable long bookId) {
        GetReviewDto newReview = reviewService.create(reviewDto, bookId);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping("/getAll/{bookId}")
    public List<GetReviewDto> getAll(@PathVariable long bookId) {
        return reviewService.getAll(bookId);
    }

    @PatchMapping("/update/{reviewId}")
    public ResponseEntity<GetReviewDto> update(@RequestBody UpdateReviewDto reviewDto, @PathVariable long reviewId) {
        GetReviewDto updatedReview = reviewService.update(reviewDto, reviewId);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable long reviewId) {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}