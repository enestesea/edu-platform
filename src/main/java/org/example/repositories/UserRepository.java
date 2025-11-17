package org.example.repositories;

import org.example.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    boolean existsByEmail(String email);
}
