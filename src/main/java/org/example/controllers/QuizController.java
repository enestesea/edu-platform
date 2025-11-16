package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Quiz;
import org.example.services.QuizService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable UUID id) {
        Optional<Quiz> quiz = quizService.findById(id);
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<Quiz> getQuizByModule(@PathVariable UUID moduleId) {
        Optional<Quiz> quiz = quizService.findByModuleId(moduleId);
        return quiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.save(quiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable UUID id, @RequestBody Quiz quiz) {
        if (!quizService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quiz.setId(id);
        return ResponseEntity.ok(quizService.save(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable UUID id) {
        if (!quizService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quizService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}