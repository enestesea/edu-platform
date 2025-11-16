package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Submission;
import org.example.repositories.SubmissionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }

    public Optional<Submission> findById(UUID id) {
        return submissionRepository.findById(id);
    }

    public List<Submission> findByAssignmentId(UUID assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }

    public List<Submission> findByStudentId(UUID studentId) {
        return submissionRepository.findByStudentId(studentId);
    }

    public Optional<Submission> findByAssignmentAndStudent(UUID assignmentId, UUID studentId) {
        return submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
    }

    public Submission save(Submission submission) {
        return submissionRepository.save(submission);
    }

    public void deleteById(UUID id) {
        submissionRepository.deleteById(id);
    }
}