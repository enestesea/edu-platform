package org.example.integration;

import org.example.entities.*;
import org.example.entities.Module;
import org.example.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BaseIntegrationTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CourseRepository courseRepository;

    @Autowired
    protected ModuleRepository moduleRepository;

    @Autowired
    protected LessonRepository lessonRepository;

    @Autowired
    protected AssignmentRepository assignmentRepository;

    @Autowired
    protected SubmissionRepository submissionRepository;

    @Autowired
    protected EnrollmentRepository enrollmentRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected QuizRepository quizRepository;

    @Autowired
    protected QuestionRepository questionRepository;

    @Autowired
    protected AnswerOptionRepository answerOptionRepository;

    @Autowired
    protected ProfileRepository profileRepository;

    @Autowired
    protected TagRepository tagRepository;

    @Autowired
    protected CourseReviewRepository courseReviewRepository;

    @Autowired
    protected QuizSubmissionRepository quizSubmissionRepository;

    protected User teacher;
    protected User student;
    protected Category category;

    @BeforeEach
    void setUp() {
        teacher = createUser("Test Teacher", "teacher@test.com", UserRole.TEACHER);
        student = createUser("Test Student", "student@test.com", UserRole.STUDENT);
        category = createCategory("Test Category");
    }

    protected User createUser(String name, String email, UserRole role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    protected Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    protected Course createTestCourse() {
        Course course = new Course();
        course.setTitle("Test Course");
        course.setDescription("Test Description");
        course.setTeacher(teacher);
        course.setCategory(category);
        return courseRepository.save(course);
    }

    protected Module createTestModule(Course course, String title, int orderIndex) {
        Module module = new Module();
        module.setTitle(title);
        module.setOrderIndex(orderIndex);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    protected Lesson createTestLesson(Module module, String title) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setContent("Test Content for " + title);
        lesson.setVideoUrl("https://example.com/video/" + title.toLowerCase().replace(" ", "-"));
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    protected Assignment createTestAssignment(Lesson lesson, String title, int maxScore) {
        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription("Test Description for " + title);
        assignment.setMaxScore(maxScore);
        assignment.setDueDate(Instant.now().plusSeconds(86400)); // +1 day
        assignment.setLesson(lesson);
        return assignmentRepository.save(assignment);
    }

    protected Quiz createTestQuiz(Module module, String title) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setModule(module);
        return quizRepository.save(quiz);
    }

    protected Question createTestQuestion(Quiz quiz, String text, String type) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(text);
        question.setType(type);
        return questionRepository.save(question);
    }

    protected AnswerOption createTestAnswerOption(Question question, String text, boolean isCorrect) {
        AnswerOption option = new AnswerOption();
        option.setQuestion(question);
        option.setText(text);
        option.setCorrect(isCorrect);
        return answerOptionRepository.save(option);
    }
}