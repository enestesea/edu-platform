package org.example.integration;

import org.example.entities.*;
import org.example.entities.Module;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UpdateOperationsTest extends BaseIntegrationTest {

    @Test
    void testUpdateCourseDetails() {
        //Обновление деталей курса
        Course course = createTestCourse();

        course.setTitle("Updated Course Title");
        course.setDescription("Updated Course Description");
        Course updatedCourse = courseRepository.save(course);

        Optional<Course> foundCourse = courseRepository.findById(course.getId());
        assertTrue(foundCourse.isPresent());
        assertEquals("Updated Course Title", foundCourse.get().getTitle());
        assertEquals("Updated Course Description", foundCourse.get().getDescription());
    }

    @Test
    void testUpdateModuleOrder() {
        //Обновление порядка модуля
        Course course = createTestCourse();
        Module module = createTestModule(course, "Test Module", 1);

        module.setOrderIndex(5);
        Module updatedModule = moduleRepository.save(module);

        Optional<Module> foundModule = moduleRepository.findById(module.getId());
        assertTrue(foundModule.isPresent());
        assertEquals(5, foundModule.get().getOrderIndex());
    }

    @Test
    void testUpdateSubmissionScoreAndFeedback() {
        //Обновление оценки и фидбека
        Course course = createTestCourse();
        Module module = createTestModule(course, "Module", 1);
        Lesson lesson = createTestLesson(module, "Lesson");
        Assignment assignment = createTestAssignment(lesson, "Assignment", 100);

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent("Initial content");
        submission.setScore(70);
        submission.setFeedback("Needs improvement");
        submission.setSubmittedAt(Instant.now());
        submission = submissionRepository.save(submission);

        submission.setScore(95);
        submission.setFeedback("Excellent work!");
        submissionRepository.save(submission);

        Optional<Submission> updatedSubmission = submissionRepository.findById(submission.getId());
        assertTrue(updatedSubmission.isPresent());
        assertEquals(95, updatedSubmission.get().getScore());
        assertEquals("Excellent work!", updatedSubmission.get().getFeedback());
        assertEquals("Initial content", updatedSubmission.get().getContent()); // Не изменилось
    }

    @Test
    void testUpdateEnrollmentStatus() {
        //Обновление статуса записи
        Course course = createTestCourse();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("PENDING");
        enrollment.setEnrollDate(Instant.now());
        enrollment = enrollmentRepository.save(enrollment);

        enrollment.setStatus("COMPLETED");
        enrollmentRepository.save(enrollment);

        Optional<Enrollment> updatedEnrollment = enrollmentRepository.findById(enrollment.getId());
        assertTrue(updatedEnrollment.isPresent());
        assertEquals("COMPLETED", updatedEnrollment.get().getStatus());
    }

    @Test
    void testUpdateUserProfile() {
        //Обновление профиля пользователя
        Profile profile = new Profile();
        profile.setUser(student);
        profile.setBio("Original bio");
        profile.setAvatarUrl("https://example.com/avatar1.jpg");
        profile = profileRepository.save(profile);

        profile.setBio("Updated bio");
        profile.setAvatarUrl("https://example.com/avatar2.jpg");
        profileRepository.save(profile);

        Optional<Profile> updatedProfile = profileRepository.findById(profile.getId());
        assertTrue(updatedProfile.isPresent());
        assertEquals("Updated bio", updatedProfile.get().getBio());
        assertEquals("https://example.com/avatar2.jpg", updatedProfile.get().getAvatarUrl());
    }

    @Test
    void testUpdateAssignmentDueDate() {
        //Обновление даты сдачи задания
        Course course = createTestCourse();
        Module module = createTestModule(course, "Module", 1);
        Lesson lesson = createTestLesson(module, "Lesson");
        Assignment assignment = createTestAssignment(lesson, "Assignment", 100);

        var originalDueDate = assignment.getDueDate();

        assignment.setDueDate(Instant.now().plusSeconds(172800)); // +2 days
        assignmentRepository.save(assignment);

        Optional<Assignment> updatedAssignment = assignmentRepository.findById(assignment.getId());
        assertTrue(updatedAssignment.isPresent());
        assertNotEquals(originalDueDate, updatedAssignment.get().getDueDate());
    }
}