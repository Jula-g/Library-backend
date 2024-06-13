package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.Loan;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUser_UserId(Long userId);

    boolean existsByUserUserId(long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Loan l WHERE l.user.userId = :userId")
    void deleteAllForUser(@Param("userId") long userId);

    boolean existsByBook_BookId(long id);
}
