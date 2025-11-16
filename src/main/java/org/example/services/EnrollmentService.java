package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Enrollment;
import org.example.repositories.EnrollmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> findAll() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(UUID id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> findByStudentId(UUID studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> findByCourseId(UUID courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public Optional<Enrollment> findByStudentAndCourse(UUID studentId, UUID courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteById(UUID id) {
        enrollmentRepository.deleteById(id);
    }
}