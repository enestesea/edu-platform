package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Assignment;
import org.example.services.AssignmentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable UUID id) {
        Optional<Assignment> assignment = assignmentService.findById(id);
        return assignment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.save(assignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable UUID id, @RequestBody Assignment assignment) {
        if (!assignmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        assignment.setId(id);
        return ResponseEntity.ok(assignmentService.save(assignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable UUID id) {
        if (!assignmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        assignmentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}