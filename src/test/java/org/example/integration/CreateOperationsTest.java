package org.example.integration;

import org.example.entities.*;
import org.example.entities.Module;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CreateOperationsTest extends BaseIntegrationTest {

    @Test
    void testCreateCourseWithModules() {
        //Создание курса с модулями
        Course course = createTestCourse();
        Module module1 = createTestModule(course, "Module 1", 1);
        Module module2 = createTestModule(course, "Module 2", 2);

        assertNotNull(course.getId());
        assertNotNull(module1.getId());
        assertNotNull(module2.getId());

        assertEquals(course.getId(), module1.getCourse().getId());
        assertEquals(course.getId(), module2.getCourse().getId());
        assertEquals(1, module1.getOrderIndex());
        assertEquals(2, module2.getOrderIndex());
    }

    @Test
    void testCreateCourseHierarchy() {
        //Создание полной иерархии курса
        Course course = createTestCourse();
        Module module = createTestModule(course, "Main Module", 1);
        Lesson lesson = createTestLesson(module, "Main Lesson");
        Assignment assignment = createTestAssignment(lesson, "Main Assignment", 100);

        assertNotNull(course.getId());
        assertNotNull(module.getId());
        assertNotNull(lesson.getId());
        assertNotNull(assignment.getId());

        assertEquals(module.getCourse().getId(), course.getId());
        assertEquals(lesson.getModule().getId(), module.getId());
        assertEquals(assignment.getLesson().getId(), lesson.getId());
    }

    @Test
    void testCreateEnrollment() {
        //Запись студента на курс
        Course course = createTestCourse();

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("ACTIVE");
        enrollment.setEnrollDate(Instant.now());
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        assertNotNull(savedEnrollment.getId());
        assertEquals("ACTIVE", savedEnrollment.getStatus());
        assertEquals(student.getId(), savedEnrollment.getStudent().getId());
        assertEquals(course.getId(), savedEnrollment.getCourse().getId());
    }

    @Test
    void testCreateSubmission() {
        //Создание отправки решения
        Course course = createTestCourse();
        Module module = createTestModule(course, "Test Module", 1);
        Lesson lesson = createTestLesson(module, "Test Lesson");
        Assignment assignment = createTestAssignment(lesson, "Test Assignment", 100);

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent("Test submission content");
        submission.setScore(85);
        submission.setFeedback("Good work!");
        submission.setSubmittedAt(Instant.now());
        Submission savedSubmission = submissionRepository.save(submission);

        assertNotNull(savedSubmission.getId());
        assertEquals(student.getId(), savedSubmission.getStudent().getId());
        assertEquals(assignment.getId(), savedSubmission.getAssignment().getId());
        assertEquals(85, savedSubmission.getScore());
        assertEquals("Good work!", savedSubmission.getFeedback());
    }

    @Test
    void testCreateQuizWithQuestions() {
        // Создание теста с вопросами и ответами
        Course course = createTestCourse();
        Module module = createTestModule(course, "Quiz Module", 1);
        Quiz quiz = createTestQuiz(module, "Final Quiz");

        Question question1 = createTestQuestion(quiz, "What is Java?", "SINGLE_CHOICE");
        Question question2 = createTestQuestion(quiz, "Select correct statements", "MULTIPLE_CHOICE");

        AnswerOption option1 = createTestAnswerOption(question1, "Programming Language", true);
        AnswerOption option2 = createTestAnswerOption(question1, "Coffee", false);
        AnswerOption option3 = createTestAnswerOption(question2, "Java is OOP", true);
        AnswerOption option4 = createTestAnswerOption(question2, "Java is compiled", true);

        assertNotNull(quiz.getId());
        assertNotNull(question1.getId());
        assertNotNull(question2.getId());
        assertNotNull(option1.getId());
        assertNotNull(option2.getId());
        assertNotNull(option3.getId());
        assertNotNull(option4.getId());

        assertEquals(quiz.getId(), question1.getQuiz().getId());
        assertEquals(question1.getId(), option1.getQuestion().getId());
        assertTrue(option1.isCorrect());
        assertFalse(option2.isCorrect());
    }

    @Test
    void testCreateCourseReview() {
        //Создание отзыва о курсе
        Course course = createTestCourse();

        CourseReview review = new CourseReview();
        review.setStudent(student);
        review.setCourse(course);
        review.setRating(5);
        review.setComment("Excellent course!");
        review.setCreatedAtReview(Instant.now());
        CourseReview savedReview = courseReviewRepository.save(review);

        assertNotNull(savedReview.getId());
        assertEquals(5, savedReview.getRating());
        assertEquals("Excellent course!", savedReview.getComment());
        assertEquals(student.getId(), savedReview.getStudent().getId());
        assertEquals(course.getId(), savedReview.getCourse().getId());
    }

    @Test
    void testCreateUserProfile() {
        //Создание профиля пользователя
        Profile profile = new Profile();
        profile.setUser(student);
        profile.setBio("Test bio");
        profile.setAvatarUrl("https://example.com/avatar.jpg");
        Profile savedProfile = profileRepository.save(profile);

        assertNotNull(savedProfile.getId());
        assertEquals("Test bio", savedProfile.getBio());
        assertEquals(student.getId(), savedProfile.getUser().getId());
    }
}