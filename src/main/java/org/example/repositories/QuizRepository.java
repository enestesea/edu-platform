package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Quiz;

import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    Optional<Quiz> findByModuleId(UUID moduleId);
}
