package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Question;
import org.example.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Optional<Question> findById(UUID id) {
        return questionRepository.findById(id);
    }

    public List<Question> findByQuizId(UUID quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public void deleteById(UUID id) {
        questionRepository.deleteById(id);
    }
}