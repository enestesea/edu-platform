package org.example.integration;

import org.example.entities.*;
import org.example.entities.Module;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReadOperationsTest extends BaseIntegrationTest {

    @Test
    void testFindCourseById() {
        //Поиск курса по ID
        Course course = createTestCourse();

        Optional<Course> foundCourse = courseRepository.findById(course.getId());

        assertTrue(foundCourse.isPresent());
        assertEquals("Test Course", foundCourse.get().getTitle());
        assertEquals("Test Description", foundCourse.get().getDescription());
        assertEquals(teacher.getId(), foundCourse.get().getTeacher().getId());
        assertEquals(category.getId(), foundCourse.get().getCategory().getId());
    }

    @Test
    void testFindCoursesByTeacher() {
        //Поиск курсов преподавателя
        Course course1 = createTestCourse();
        Course course2 = new Course();
        course2.setTitle("Another Course");
        course2.setDescription("Another Description");
        course2.setTeacher(teacher);
        course2.setCategory(category);
        courseRepository.save(course2);

        List<Course> teacherCourses = courseRepository.findByTeacherId(teacher.getId());

        assertEquals(2, teacherCourses.size());
        assertTrue(teacherCourses.stream().anyMatch(c -> c.getTitle().equals("Test Course")));
        assertTrue(teacherCourses.stream().anyMatch(c -> c.getTitle().equals("Another Course")));
    }

    @Test
    void testFindEnrollmentsByStudent() {
        //Поиск записей студента
        Course course1 = createTestCourse();
        Course course2 = createTestCourse();
        course2.setTitle("Second Course");
        courseRepository.save(course2);

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student);
        enrollment1.setCourse(course1);
        enrollment1.setStatus("ACTIVE");
        enrollment1.setEnrollDate(Instant.now());
        enrollmentRepository.save(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student);
        enrollment2.setCourse(course2);
        enrollment2.setStatus("COMPLETED");
        enrollment2.setEnrollDate(Instant.now());
        enrollmentRepository.save(enrollment2);

        List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getId());

        assertEquals(2, studentEnrollments.size());
        assertEquals(2, studentEnrollments.stream()
                .map(e -> e.getCourse().getId())
                .distinct()
                .count());
    }

    @Test
    void testFindModulesByCourse() {
        //Поиск модулей курса
        Course course = createTestCourse();
        createTestModule(course, "Module 1", 1);
        createTestModule(course, "Module 2", 2);
        createTestModule(course, "Module 3", 3);

        List<Module> modules = moduleRepository.findByCourseId(course.getId());

        assertEquals(3, modules.size());
        assertTrue(modules.stream().allMatch(m -> m.getCourse().getId().equals(course.getId())));
        assertEquals(1, modules.get(0).getOrderIndex());
        assertEquals(2, modules.get(1).getOrderIndex());
        assertEquals(3, modules.get(2).getOrderIndex());
    }

    @Test
    void testFindSubmissionsByAssignment() {
        //Поиск отправок по заданию
        Course course = createTestCourse();
        Module module = createTestModule(course, "Module", 1);
        Lesson lesson = createTestLesson(module, "Lesson");
        Assignment assignment = createTestAssignment(lesson, "Assignment", 100);

        Submission submission1 = new Submission();
        submission1.setStudent(student);
        submission1.setAssignment(assignment);
        submission1.setContent("First submission");
        submission1.setScore(80);
        submission1.setSubmittedAt(Instant.now());
        submissionRepository.save(submission1);

        User anotherStudent = createUser("Another Student", "another@test.com", UserRole.STUDENT);
        Submission submission2 = new Submission();
        submission2.setStudent(anotherStudent);
        submission2.setAssignment(assignment);
        submission2.setContent("Second submission");
        submission2.setScore(90);
        submission2.setSubmittedAt(Instant.now());
        submissionRepository.save(submission2);
        List<Submission> submissions = submissionRepository.findByAssignmentId(assignment.getId());
        assertEquals(2, submissions.size());
    }

    @Test
    void testFindQuestionsByQuiz() {
        //Поиск вопросов теста
        Course course = createTestCourse();
        Module module = createTestModule(course, "Quiz Module", 1);
        Quiz quiz = createTestQuiz(module, "Test Quiz");

        createTestQuestion(quiz, "Question 1", "SINGLE_CHOICE");
        createTestQuestion(quiz, "Question 2", "MULTIPLE_CHOICE");
        createTestQuestion(quiz, "Question 3", "TRUE_FALSE");
        List<Question> questions = questionRepository.findByQuizId(quiz.getId());
        assertEquals(3, questions.size());
    }
}