package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Submission;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findByAssignmentId(UUID assignmentId);
    List<Submission> findByStudentId(UUID studentId);
    Optional<Submission> findByAssignmentIdAndStudentId(UUID assignmentId, UUID studentId);
}
