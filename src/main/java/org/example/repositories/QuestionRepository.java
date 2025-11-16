package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByQuizId(UUID quizId);
}
