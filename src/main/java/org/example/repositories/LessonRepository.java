package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByModuleId(UUID moduleId);
}
