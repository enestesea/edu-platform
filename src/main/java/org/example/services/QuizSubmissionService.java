package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.QuizSubmission;
import org.example.repositories.QuizSubmissionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuizSubmissionService {

    private final QuizSubmissionRepository quizSubmissionRepository;

    public QuizSubmissionService(QuizSubmissionRepository quizSubmissionRepository) {
        this.quizSubmissionRepository = quizSubmissionRepository;
    }

    public List<QuizSubmission> findAll() {
        return quizSubmissionRepository.findAll();
    }

    public Optional<QuizSubmission> findById(UUID id) {
        return quizSubmissionRepository.findById(id);
    }

    public List<QuizSubmission> findByStudentId(UUID studentId) {
        return quizSubmissionRepository.findByStudentId(studentId);
    }

    public List<QuizSubmission> findByQuizId(UUID quizId) {
        return quizSubmissionRepository.findByQuizId(quizId);
    }

    public Optional<QuizSubmission> findByQuizAndStudent(UUID quizId, UUID studentId) {
        return quizSubmissionRepository.findByQuizIdAndStudentId(quizId, studentId);
    }

    public QuizSubmission save(QuizSubmission quizSubmission) {
        return quizSubmissionRepository.save(quizSubmission);
    }

    public void deleteById(UUID id) {
        quizSubmissionRepository.deleteById(id);
    }
}