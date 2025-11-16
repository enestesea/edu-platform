package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Lesson;
import org.example.repositories.LessonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> findById(UUID id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> findByModuleId(UUID moduleId) {
        return lessonRepository.findByModuleId(moduleId);
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteById(UUID id) {
        lessonRepository.deleteById(id);
    }
}