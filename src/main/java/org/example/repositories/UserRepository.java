package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
