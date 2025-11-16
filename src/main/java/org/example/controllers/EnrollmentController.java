package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Enrollment;
import org.example.services.EnrollmentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable UUID id) {
        Optional<Enrollment> enrollment = enrollmentService.findById(id);
        return enrollment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable UUID studentId) {
        return enrollmentService.findByStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable UUID courseId) {
        return enrollmentService.findByCourseId(courseId);
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Enrollment> getEnrollmentByStudentAndCourse(
            @PathVariable UUID studentId, @PathVariable UUID courseId) {
        Optional<Enrollment> enrollment = enrollmentService.findByStudentAndCourse(studentId, courseId);
        return enrollment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.save(enrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable UUID id, @RequestBody Enrollment enrollment) {
        if (!enrollmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        enrollment.setId(id);
        return ResponseEntity.ok(enrollmentService.save(enrollment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable UUID id) {
        if (!enrollmentService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        enrollmentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}