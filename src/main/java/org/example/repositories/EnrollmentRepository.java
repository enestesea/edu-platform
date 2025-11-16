package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Enrollment;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findByStudentId(UUID studentId);
    List<Enrollment> findByCourseId(UUID courseId);
    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
}
