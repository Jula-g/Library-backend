package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByBookBookId(long bookId);
    Optional<Review> findByBookBookIdAndUserUserId(long bookId, long userId);
}
