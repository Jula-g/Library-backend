package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}

