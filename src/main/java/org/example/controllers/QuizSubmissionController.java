package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.QuizSubmission;
import org.example.services.QuizSubmissionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/quiz-submissions")
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;

    public QuizSubmissionController(QuizSubmissionService quizSubmissionService) {
        this.quizSubmissionService = quizSubmissionService;
    }

    @GetMapping
    public List<QuizSubmission> getAllQuizSubmissions() {
        return quizSubmissionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSubmission> getQuizSubmissionById(@PathVariable UUID id) {
        Optional<QuizSubmission> quizSubmission = quizSubmissionService.findById(id);
        return quizSubmission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<QuizSubmission> getQuizSubmissionsByStudent(@PathVariable UUID studentId) {
        return quizSubmissionService.findByStudentId(studentId);
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuizSubmission> getQuizSubmissionsByQuiz(@PathVariable UUID quizId) {
        return quizSubmissionService.findByQuizId(quizId);
    }

    @GetMapping("/quiz/{quizId}/student/{studentId}")
    public ResponseEntity<QuizSubmission> getQuizSubmissionByQuizAndStudent(
            @PathVariable UUID quizId, @PathVariable UUID studentId) {
        Optional<QuizSubmission> quizSubmission = quizSubmissionService.findByQuizAndStudent(quizId, studentId);
        return quizSubmission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizSubmission createQuizSubmission(@RequestBody QuizSubmission quizSubmission) {
        return quizSubmissionService.save(quizSubmission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizSubmission> updateQuizSubmission(@PathVariable UUID id, @RequestBody QuizSubmission quizSubmission) {
        if (!quizSubmissionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quizSubmission.setId(id);
        return ResponseEntity.ok(quizSubmissionService.save(quizSubmission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizSubmission(@PathVariable UUID id) {
        if (!quizSubmissionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        quizSubmissionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}