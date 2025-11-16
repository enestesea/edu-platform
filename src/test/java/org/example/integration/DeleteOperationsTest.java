package org.example.integration;

import org.example.entities.*;
import org.example.entities.Module;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeleteOperationsTest extends BaseIntegrationTest {

    @Test
    void testDeleteCourse() {
        //Удаление курса
        Course course = createTestCourse();

        courseRepository.delete(course);

        Optional<Course> deletedCourse = courseRepository.findById(course.getId());
        assertFalse(deletedCourse.isPresent());
    }

    @Test
    void testDeleteModule() {
        //Удаление модуля
        Course course = createTestCourse();
        Module module = createTestModule(course, "Test Module", 1);

        moduleRepository.delete(module);

        Optional<Module> deletedModule = moduleRepository.findById(module.getId());
        assertFalse(deletedModule.isPresent());

        //Проверка, что курс остался
        Optional<Course> existingCourse = courseRepository.findById(course.getId());
        assertTrue(existingCourse.isPresent());
    }

    @Test
    void testDeleteSubmission() {
        //Удаление отправки
        Course course = createTestCourse();
        Module module = createTestModule(course, "Module", 1);
        Lesson lesson = createTestLesson(module, "Lesson");
        Assignment assignment = createTestAssignment(lesson, "Assignment", 100);

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent("Content to delete");
        submission.setScore(80);
        submission.setSubmittedAt(Instant.now());
        submission = submissionRepository.save(submission);

        submissionRepository.delete(submission);

        Optional<Submission> deletedSubmission = submissionRepository.findById(submission.getId());
        assertFalse(deletedSubmission.isPresent());

        // Проверка, что студент и задание остались
        assertTrue(userRepository.findById(student.getId()).isPresent());
        assertTrue(assignmentRepository.findById(assignment.getId()).isPresent());
    }

    @Test
    void testDeleteEnrollment() {
        //Удаление записи на курс
        Course course = createTestCourse();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("ACTIVE");
        enrollment.setEnrollDate(Instant.now());
        enrollment = enrollmentRepository.save(enrollment);

        enrollmentRepository.delete(enrollment);

        Optional<Enrollment> deletedEnrollment = enrollmentRepository.findById(enrollment.getId());
        assertFalse(deletedEnrollment.isPresent());

        assertTrue(userRepository.findById(student.getId()).isPresent());
        assertTrue(courseRepository.findById(course.getId()).isPresent());
    }

    @Test
    void testDeleteUserWithProfile() {
        //Удаление пользователя чтобы профиль тоже удалился
        Profile profile = new Profile();
        profile.setBio("Test bio");
        profile.setAvatarUrl("https://example.com/avatar.jpg");

        student.setProfile(profile);

        User savedUser = userRepository.save(student);

        UUID profileId = savedUser.getProfile().getId();
        assertNotNull(profileId);

        Optional<Profile> savedProfile = profileRepository.findById(profileId);
        assertTrue(savedProfile.isPresent());

        userRepository.delete(savedUser);

        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());

        //Проверяем каскадное удаление профиля
        Optional<Profile> deletedProfile = profileRepository.findById(profileId);
        assertFalse(deletedProfile.isPresent());
    }

    @Test
    void testDeleteQuizWithQuestions() {
        //Удаление теста с вопросами и ответами
        Course course = createTestCourse();
        Module module = createTestModule(course, "Quiz Module", 1);
        Quiz quiz = createTestQuiz(module, "Test Quiz");

        Question question = createTestQuestion(quiz, "Test Question", "SINGLE_CHOICE");

        quiz.getQuestions().add(question);

        AnswerOption option1 = createTestAnswerOption(question, "Correct", true);
        AnswerOption option2 = createTestAnswerOption(question, "Incorrect", false);
        question.getAnswerOptions().add(option1);
        question.getAnswerOptions().add(option2);

        Quiz savedQuiz = quizRepository.save(quiz);

        UUID quizId = savedQuiz.getId();
        UUID questionId = question.getId();
        UUID option1Id = option1.getId();
        UUID option2Id = option2.getId();

        assertTrue(quizRepository.findById(quizId).isPresent());
        assertTrue(questionRepository.findById(questionId).isPresent());
        assertTrue(answerOptionRepository.findById(option1Id).isPresent());
        assertTrue(answerOptionRepository.findById(option2Id).isPresent());

        quizRepository.delete(savedQuiz);

        Optional<Quiz> deletedQuiz = quizRepository.findById(quizId);
        assertFalse(deletedQuiz.isPresent());

        Optional<Question> deletedQuestion = questionRepository.findById(questionId);
        assertFalse(deletedQuestion.isPresent());

        Optional<AnswerOption> deletedOption1 = answerOptionRepository.findById(option1Id);
        Optional<AnswerOption> deletedOption2 = answerOptionRepository.findById(option2Id);
        assertFalse(deletedOption1.isPresent());
        assertFalse(deletedOption2.isPresent());
    }
}