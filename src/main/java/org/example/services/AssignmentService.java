package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Assignment;
import org.example.repositories.AssignmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> findById(UUID id) {
        return assignmentRepository.findById(id);
    }

    public List<Assignment> findByLessonId(UUID lessonId) {
        return assignmentRepository.findByLessonId(lessonId);
    }

    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteById(UUID id) {
        assignmentRepository.deleteById(id);
    }
}