package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.CourseReview;
import org.example.services.CourseReviewService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class CourseReviewController {

    private final CourseReviewService courseReviewService;

    public CourseReviewController(CourseReviewService courseReviewService) {
        this.courseReviewService = courseReviewService;
    }

    @GetMapping
    public List<CourseReview> getAllReviews() {
        return courseReviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseReview> getReviewById(@PathVariable UUID id) {
        Optional<CourseReview> review = courseReviewService.findById(id);
        return review.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<CourseReview> getReviewsByCourse(@PathVariable UUID courseId) {
        return courseReviewService.findByCourseId(courseId);
    }

    @GetMapping("/student/{studentId}")
    public List<CourseReview> getReviewsByStudent(@PathVariable UUID studentId) {
        return courseReviewService.findByStudentId(studentId);
    }

    @PostMapping
    public CourseReview createReview(@RequestBody CourseReview review) {
        return courseReviewService.save(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseReview> updateReview(@PathVariable UUID id, @RequestBody CourseReview review) {
        if (!courseReviewService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        review.setId(id);
        return ResponseEntity.ok(courseReviewService.save(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        if (!courseReviewService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        courseReviewService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}