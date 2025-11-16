package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Assignment;

import java.util.List;
import java.util.UUID;

public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
    List<Assignment> findByLessonId(UUID lessonId);
}
