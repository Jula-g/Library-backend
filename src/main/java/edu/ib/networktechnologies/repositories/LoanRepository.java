package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser_UserId(long userId);
}
