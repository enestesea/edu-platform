package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Lesson;
import org.example.services.LessonService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable UUID id) {
        Optional<Lesson> lesson = lessonService.findById(id);
        return lesson.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/module/{moduleId}")
    public List<Lesson> getLessonsByModule(@PathVariable UUID moduleId) {
        return lessonService.findByModuleId(moduleId);
    }

    @PostMapping
    public Lesson createLesson(@RequestBody Lesson lesson) {
        return lessonService.save(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable UUID id, @RequestBody Lesson lesson) {
        if (!lessonService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lesson.setId(id);
        return ResponseEntity.ok(lessonService.save(lesson));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable UUID id) {
        if (!lessonService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        lessonService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}