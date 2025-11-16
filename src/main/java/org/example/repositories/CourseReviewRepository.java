package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.CourseReview;

import java.util.List;
import java.util.UUID;

public interface CourseReviewRepository extends JpaRepository<CourseReview, UUID> {
    List<CourseReview> findByCourseId(UUID courseId);
    List<CourseReview> findByStudentId(UUID studentId);
}
