package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.AnswerOption;
import org.example.services.AnswerOptionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/answer-options")

public class AnswerOptionController {

    private final AnswerOptionService answerOptionService;

    public AnswerOptionController(AnswerOptionService answerOptionService) {
        this.answerOptionService = answerOptionService;
    }

    @GetMapping
    public List<AnswerOption> getAllAnswerOptions() {
        return answerOptionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerOption> getAnswerOptionById(@PathVariable UUID id) {
        Optional<AnswerOption> answerOption = answerOptionService.findById(id);
        return answerOption.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/question/{questionId}")
    public List<AnswerOption> getAnswerOptionsByQuestion(@PathVariable UUID questionId) {
        return answerOptionService.findByQuestionId(questionId);
    }

    @PostMapping
    public AnswerOption createAnswerOption(@RequestBody AnswerOption answerOption) {
        return answerOptionService.save(answerOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerOption> updateAnswerOption(@PathVariable UUID id, @RequestBody AnswerOption answerOption) {
        if (!answerOptionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        answerOption.setId(id);
        return ResponseEntity.ok(answerOptionService.save(answerOption));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswerOption(@PathVariable UUID id) {
        if (!answerOptionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        answerOptionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}