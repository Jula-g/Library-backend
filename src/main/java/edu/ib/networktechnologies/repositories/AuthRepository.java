package edu.ib.networktechnologies.repositories;

import edu.ib.networktechnologies.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByUsername(String username);
}
