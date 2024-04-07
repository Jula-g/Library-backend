package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
