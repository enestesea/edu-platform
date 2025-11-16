package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Course;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByTeacherId(UUID teacherId);
    List<Course> findByCategoryId(UUID categoryId);
    List<Course> findByCategory_Name(String categoryName);
}

