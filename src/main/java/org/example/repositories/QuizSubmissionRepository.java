package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.QuizSubmission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, UUID> {
    List<QuizSubmission> findByStudentId(UUID studentId);
    List<QuizSubmission> findByQuizId(UUID quizId);
    Optional<QuizSubmission> findByQuizIdAndStudentId(UUID quizId, UUID studentId);
}
