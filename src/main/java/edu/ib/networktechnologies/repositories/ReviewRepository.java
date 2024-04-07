package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
