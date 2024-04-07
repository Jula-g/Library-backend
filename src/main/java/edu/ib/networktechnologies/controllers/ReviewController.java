package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.review.CreateReviewDto;
import edu.ib.networktechnologies.controllers.dto.review.CreateReviewResponseDto;
import edu.ib.networktechnologies.controllers.dto.review.GetReviewDto;
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
    public ResponseEntity<CreateReviewResponseDto> create(@RequestBody CreateReviewDto reviewDto, @PathVariable long bookId) {
        CreateReviewResponseDto newReview = reviewService.create(reviewDto, bookId);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping("/getAll/{bookId}") // Add bookId as a path variable
    public List<GetReviewDto> getAll(@PathVariable long bookId) {
        return reviewService.getAll(bookId);
    }

    @GetMapping("/{bookId}")
    public GetReviewDto getOne(@PathVariable long bookId) {
        return reviewService.getOne(bookId);
    }
}