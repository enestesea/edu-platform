package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Course;
import org.example.repositories.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(UUID id) {
        return courseRepository.findById(id);
    }

    public List<Course> findByTeacherId(UUID teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    public List<Course> findByCategoryId(UUID categoryId) {
        return courseRepository.findByCategoryId(categoryId);
    }

    public List<Course> findByCategoryName(String categoryName) {
        return courseRepository.findByCategory_Name(categoryName);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(UUID id) {
        courseRepository.deleteById(id);
    }
}