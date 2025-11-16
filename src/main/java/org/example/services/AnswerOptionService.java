package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.AnswerOption;
import org.example.repositories.AnswerOptionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerOptionService {

    private final AnswerOptionRepository answerOptionRepository;

    public AnswerOptionService(AnswerOptionRepository answerOptionRepository) {
        this.answerOptionRepository = answerOptionRepository;
    }

    public List<AnswerOption> findAll() {
        return answerOptionRepository.findAll();
    }

    public Optional<AnswerOption> findById(UUID id) {
        return answerOptionRepository.findById(id);
    }

    public List<AnswerOption> findByQuestionId(UUID questionId) {
        return answerOptionRepository.findByQuestionId(questionId);
    }

    public AnswerOption save(AnswerOption answerOption) {
        return answerOptionRepository.save(answerOption);
    }

    public void deleteById(UUID id) {
        answerOptionRepository.deleteById(id);
    }
}