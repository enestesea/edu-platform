package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Question;
import org.example.services.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable UUID id) {
        Optional<Question> question = questionService.findById(id);
        return question.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsByQuiz(@PathVariable UUID quizId) {
        return questionService.findByQuizId(quizId);
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.save(question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable UUID id, @RequestBody Question question) {
        if (!questionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        question.setId(id);
        return ResponseEntity.ok(questionService.save(question));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        if (!questionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        questionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}