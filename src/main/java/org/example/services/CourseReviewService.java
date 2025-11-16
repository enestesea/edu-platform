package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.CourseReview;
import org.example.repositories.CourseReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseReviewService {

    private final CourseReviewRepository courseReviewRepository;

    public CourseReviewService(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    public List<CourseReview> findAll() {
        return courseReviewRepository.findAll();
    }

    public Optional<CourseReview> findById(UUID id) {
        return courseReviewRepository.findById(id);
    }

    public List<CourseReview> findByCourseId(UUID courseId) {
        return courseReviewRepository.findByCourseId(courseId);
    }

    public List<CourseReview> findByStudentId(UUID studentId) {
        return courseReviewRepository.findByStudentId(studentId);
    }

    public CourseReview save(CourseReview courseReview) {
        return courseReviewRepository.save(courseReview);
    }

    public void deleteById(UUID id) {
        courseReviewRepository.deleteById(id);
    }
}