package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Submission;
import org.example.services.SubmissionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return submissionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable UUID id) {
        Optional<Submission> submission = submissionService.findById(id);
        return submission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<Submission> getSubmissionsByAssignment(@PathVariable UUID assignmentId) {
        return submissionService.findByAssignmentId(assignmentId);
    }

    @GetMapping("/student/{studentId}")
    public List<Submission> getSubmissionsByStudent(@PathVariable UUID studentId) {
        return submissionService.findByStudentId(studentId);
    }

    @GetMapping("/assignment/{assignmentId}/student/{studentId}")
    public ResponseEntity<Submission> getSubmissionByAssignmentAndStudent(
            @PathVariable UUID assignmentId, @PathVariable UUID studentId) {
        Optional<Submission> submission = submissionService.findByAssignmentAndStudent(assignmentId, studentId);
        return submission.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Submission createSubmission(@RequestBody Submission submission) {
        return submissionService.save(submission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> updateSubmission(@PathVariable UUID id, @RequestBody Submission submission) {
        if (!submissionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        submission.setId(id);
        return ResponseEntity.ok(submissionService.save(submission));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable UUID id) {
        if (!submissionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        submissionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}