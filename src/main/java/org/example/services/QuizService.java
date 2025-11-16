package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Quiz;
import org.example.repositories.QuizRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> findById(UUID id) {
        return quizRepository.findById(id);
    }

    public Optional<Quiz> findByModuleId(UUID moduleId) {
        return quizRepository.findByModuleId(moduleId);
    }

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void deleteById(UUID id) {
        quizRepository.deleteById(id);
    }
}