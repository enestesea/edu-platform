package org.example.config;

import org.example.entities.*;
import org.example.entities.Module;
import org.example.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SubmissionRepository submissionRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final ProfileRepository profileRepository;
    private final TagRepository tagRepository;
    private final CourseReviewRepository courseReviewRepository;

    public DataLoader(UserRepository userRepository,
                      CategoryRepository categoryRepository,
                      CourseRepository courseRepository,
                      ModuleRepository moduleRepository,
                      LessonRepository lessonRepository,
                      AssignmentRepository assignmentRepository,
                      QuizRepository quizRepository,
                      QuestionRepository questionRepository,
                      AnswerOptionRepository answerOptionRepository,
                      EnrollmentRepository enrollmentRepository,
                      SubmissionRepository submissionRepository,
                      QuizSubmissionRepository quizSubmissionRepository,
                      ProfileRepository profileRepository,
                      TagRepository tagRepository,
                      CourseReviewRepository courseReviewRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.assignmentRepository = assignmentRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.submissionRepository = submissionRepository;
        this.quizSubmissionRepository = quizSubmissionRepository;
        this.profileRepository = profileRepository;
        this.tagRepository = tagRepository;
        this.courseReviewRepository = courseReviewRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        logger.info("Проверка наличия тестовых данных...");
        if (userRepository.count() == 0) {
            logger.info("База данных пуста, начинаю создание тестовых данных");
            createTestData();
        } else {
            logger.info("Тестовые данные уже существуют, пропускаю создание");
            printStatistics();
        }
    }

    private void createTestData() {
        logger.info("=== НАЧАЛО СОЗДАНИЯ ТЕСТОВЫХ ДАННЫХ ===");

        //Создание пользователей
        logger.info("Создание пользователей...");
        User teacher = createTeacher();
        User student1 = createStudent("Иван", "Петров", "ivan@example.com");
        User student2 = createStudent("Мария", "Сидорова", "maria@example.com");
        logger.info("Создано пользователей: преподаватель - {}, студенты - {}, {}",
                teacher.getName(), student1.getName(), student2.getName());

        //Создание профилей
        logger.info("Создание профилей пользователей...");
        createProfiles(teacher, student1, student2);

        //Создание категорий
        logger.info("Создание категорий...");
        Category programmingCategory = createCategory("Программирование");
        Category databaseCategory = createCategory("Базы данных");
        logger.info("Созданы категории: {}, {}", programmingCategory.getName(), databaseCategory.getName());

        //Создание тегов
        logger.info("Создание тегов...");
        Tag javaTag = createTag("Java");
        Tag hibernateTag = createTag("Hibernate");
        Tag sqlTag = createTag("SQL");
        Tag springTag = createTag("Spring");
        logger.info("Созданы теги: Java, Hibernate, SQL, Spring");

        //Создание курса
        logger.info("Создание курса...");
        Course hibernateCourse = createHibernateCourse(teacher, programmingCategory);
        logger.info("Создан курс: '{}'", hibernateCourse.getTitle());

        //Добавление тегов к курсу
        logger.info("Добавление тегов к курсу...");
        hibernateCourse.getTags().addAll(Arrays.asList(javaTag, hibernateTag, sqlTag));
        courseRepository.save(hibernateCourse);
        logger.info("Добавлено {} тегов к курсу", hibernateCourse.getTags().size());

        //Создание модулей
        logger.info("Создание модулей курса...");
        Module module1 = createModule("Введение в Hibernate", 1, hibernateCourse);
        Module module2 = createModule("Продвинутые возможности Hibernate", 2, hibernateCourse);
        logger.info("Созданы модули: '{}' (порядок: {}), '{}' (порядок: {})",
                module1.getTitle(), module1.getOrderIndex(), module2.getTitle(), module2.getOrderIndex());

        //Создание уроков
        logger.info("Создание уроков...");
        Lesson lesson1 = createLesson("Основы ORM", "Введение в Object-Relational Mapping...",
                "https://example.com/video1", module1);
        Lesson lesson2 = createLesson("Сущности и маппинг", "Работа с Entity классами...",
                "https://example.com/video2", module1);
        Lesson lesson3 = createLesson("HQL и Criteria API", "Изучение Hibernate Query Language...",
                "https://example.com/video3", module2);
        logger.info("Создано {} уроков в модуле '{}' и {} уроков в модуле '{}'",
                2, module1.getTitle(), 1, module2.getTitle());

        //Создание заданий
        logger.info("Создание заданий...");
        Assignment assignment1 = createAssignment("Практика: Создание сущностей",
                "Создайте Entity классы для вашего проекта...", 100,
                LocalDateTime.now().plusDays(7), lesson1);

        Assignment assignment2 = createAssignment("Практика: Написание HQL запросов",
                "Напишите HQL запросы для различных сценариев...", 100,
                LocalDateTime.now().plusDays(14), lesson3);
        logger.info("Созданы задания: '{}' (макс. балл: {}), '{}' (макс. балл: {})",
                assignment1.getTitle(), assignment1.getMaxScore(),
                assignment2.getTitle(), assignment2.getMaxScore());

        //Создание тестов
        logger.info("Создание тестов...");
        Quiz quiz1 = createQuiz("Тест по основам Hibernate", module1);
        createQuizQuestions(quiz1);
        logger.info("Создан тест: '{}' с вопросами", quiz1.getTitle());

        //Запись студентов на курс
        logger.info("Запись студентов на курс...");
        Enrollment enrollment1 = enrollStudent(student1, hibernateCourse);
        Enrollment enrollment2 = enrollStudent(student2, hibernateCourse);
        logger.info("Студенты записаны на курс: {}, {}", student1.getName(), student2.getName());

        //Имитация отправки решений
        logger.info("Создание отправленных решений...");
        Submission submission1 = createSubmission(student1, assignment1,
                "Мои Entity классы: User, Product, Order...", 85, "Хорошая работа!");
        logger.info("Создано решение задания '{}' от студента {} с оценкой {}",
                assignment1.getTitle(), student1.getName(), submission1.getScore());

        //Имитация прохождения теста
        logger.info("Создание результатов тестирования...");
        QuizSubmission quizSubmission = createQuizSubmission(student1, quiz1, 90);
        logger.info("Создан результат теста '{}' от студента {} с оценкой {}",
                quiz1.getTitle(), student1.getName(), quizSubmission.getScore());

        //Создание отзывов о курсе
        logger.info("Создание отзывов о курсе...");
        createCourseReview(student1, hibernateCourse, 5, "Отличный курс! Очень подробно объясняются основы Hibernate.");
        logger.info("Создан отзыв о курсе от студента {} с рейтингом {}", student1.getName(), 5);

        //Вывод статистики
        printStatistics();
        testRelationships();

        logger.info("=== ТЕСТОВЫЕ ДАННЫЕ УСПЕШНО СОЗДАНЫ ===");
    }

    private void printStatistics() {
        logger.info("=== СТАТИСТИКА БАЗЫ ДАННЫХ ===");
        logger.info("Пользователей: {}", userRepository.count());
        logger.info("Профилей: {}", profileRepository.count());
        logger.info("Категорий: {}", categoryRepository.count());
        logger.info("Тегов: {}", tagRepository.count());
        logger.info("Курсов: {}", courseRepository.count());
        logger.info("Модулей: {}", moduleRepository.count());
        logger.info("Уроков: {}", lessonRepository.count());
        logger.info("Заданий: {}", assignmentRepository.count());
        logger.info("Тестов: {}", quizRepository.count());
        logger.info("Вопросов: {}", questionRepository.count());
        logger.info("Вариантов ответов: {}", answerOptionRepository.count());
        logger.info("Записей на курсы: {}", enrollmentRepository.count());
        logger.info("Отправленных решений: {}", submissionRepository.count());
        logger.info("Результатов тестов: {}", quizSubmissionRepository.count());
        logger.info("Отзывов о курсах: {}", courseReviewRepository.count());
    }

    private void testRelationships() {
        logger.info("=== ПРОВЕРКА СВЯЗЕЙ МЕЖДУ СУЩНОСТЯМИ ===");

        try {
            //Проверка курса и его связей
            Course course = courseRepository.findAll().get(0);
            logger.info("Курс '{}' имеет:", course.getTitle());
            logger.info("  - Преподавателя: {}", course.getTeacher().getName());
            logger.info("  - Категорию: {}", course.getCategory().getName());
            logger.info("  - Тегов: {}", course.getTags().size());
            logger.info("  - Модулей: {}", course.getModules().size());

            //Проверка модулей и уроков
            course.getModules().forEach(module -> {
                logger.info("  Модуль '{}' (порядок: {}) содержит уроков: {}",
                        module.getTitle(), module.getOrderIndex(), module.getLessons().size());
            });

            //Проверка записей на курс
            List<Enrollment> enrollments = enrollmentRepository.findAll();
            logger.info("Записей на курс: {}", enrollments.size());
            enrollments.forEach(enrollment -> {
                logger.info("  - {} -> {} (статус: {})",
                        enrollment.getStudent().getName(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getStatus());
            });

            logger.info("Все связи проверены успешно");
        } catch (Exception e) {
            logger.error("Ошибка при проверке связей: {}", e.getMessage());
        }
    }

    private User createTeacher() {
        User teacher = new User();
        teacher.setName("Алексей Преподаватель");
        teacher.setEmail("alexey@example.com");
        teacher.setRole(UserRole.TEACHER);
        User saved = userRepository.save(teacher);
        logger.debug("Создан преподаватель: {} (ID: {})", saved.getName(), saved.getId());
        return saved;
    }

    private User createStudent(String firstName, String lastName, String email) {
        User student = new User();
        student.setName(firstName + " " + lastName);
        student.setEmail(email);
        student.setRole(UserRole.STUDENT);
        User saved = userRepository.save(student);
        logger.debug("Создан студент: {} (ID: {})", saved.getName(), saved.getId());
        return saved;
    }

    private void createProfiles(User teacher, User student1, User student2) {
        createProfile(teacher, "Опытный преподаватель Java и Hibernate", "https://example.com/avatar1.jpg");
        createProfile(student1, "Студент, изучающий Java разработку", "https://example.com/avatar2.jpg");
        createProfile(student2, "Начинающий программист", "https://example.com/avatar3.jpg");
        logger.debug("Созданы профили для всех пользователей");
    }

    private Profile createProfile(User user, String bio, String avatarUrl) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setBio(bio);
        profile.setAvatarUrl(avatarUrl);
        Profile saved = profileRepository.save(profile);
        logger.debug("Создан профиль для пользователя: {}", user.getName());
        return saved;
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        Category saved = categoryRepository.save(category);
        logger.debug("Создана категория: {} (ID: {})", saved.getName(), saved.getId());
        return saved;
    }

    private Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        Tag saved = tagRepository.save(tag);
        logger.debug("Создан тег: {} (ID: {})", saved.getName(), saved.getId());
        return saved;
    }

    private Course createHibernateCourse(User teacher, Category category) {
        Course course = new Course();
        course.setTitle("Основы Hibernate");
        course.setDescription("Полный курс по изучению Hibernate ORM для Java разработчиков");
        course.setTeacher(teacher);
        course.setCategory(category);
        Course saved = courseRepository.save(course);
        logger.debug("Создан курс: {} (ID: {})", saved.getTitle(), saved.getId());
        return saved;
    }

    private Module createModule(String title, int orderIndex, Course course) {
        Module module = new Module();
        module.setTitle(title);
        module.setOrderIndex(orderIndex);
        module.setCourse(course);
        Module saved = moduleRepository.save(module);
        logger.debug("Создан модуль: {} (порядок: {}, ID: {})",
                saved.getTitle(), saved.getOrderIndex(), saved.getId());
        return saved;
    }

    private Lesson createLesson(String title, String content, String videoUrl, Module module) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setVideoUrl(videoUrl);
        lesson.setModule(module);
        Lesson saved = lessonRepository.save(lesson);
        logger.debug("Создан урок: {} (модуль: {}, ID: {})",
                saved.getTitle(), module.getTitle(), saved.getId());
        return saved;
    }

    private Assignment createAssignment(String title, String description, int maxScore,
                                        LocalDateTime dueDate, Lesson lesson) {
        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setMaxScore(maxScore);
        assignment.setDueDate(dueDate.atZone(java.time.ZoneId.systemDefault()).toInstant());
        assignment.setLesson(lesson);
        Assignment saved = assignmentRepository.save(assignment);
        logger.debug("Создано задание: {} (макс. балл: {}, ID: {})",
                saved.getTitle(), saved.getMaxScore(), saved.getId());
        return saved;
    }

    private Quiz createQuiz(String title, Module module) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setModule(module);
        Quiz saved = quizRepository.save(quiz);
        logger.debug("Создан тест: {} (модуль: {}, ID: {})",
                saved.getTitle(), module.getTitle(), saved.getId());
        return saved;
    }

    private void createQuizQuestions(Quiz quiz) {
        Question question1 = createQuestion(quiz, "Что такое Hibernate?", "SINGLE_CHOICE");
        createAnswerOptions(question1,
                Arrays.asList("Фреймворк для веб-разработки", "ORM фреймворк", "База данных", "Язык программирования"),
                Arrays.asList(false, true, false, false));

        Question question2 = createQuestion(quiz, "Какие аннотации используются в Hibernate?", "MULTIPLE_CHOICE");
        createAnswerOptions(question2,
                Arrays.asList("@Entity", "@Table", "@Column", "@Controller"),
                Arrays.asList(true, true, true, false));

        Question question3 = createQuestion(quiz, "Hibernate поддерживает наследование", "TRUE_FALSE");
        createAnswerOptions(question3,
                Arrays.asList("True", "False"),
                Arrays.asList(true, false));

        logger.debug("Создано {} вопросов для теста '{}'", 3, quiz.getTitle());
    }

    private Question createQuestion(Quiz quiz, String text, String type) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setText(text);
        question.setType(type);
        Question saved = questionRepository.save(question);
        logger.debug("Создан вопрос: {} (тип: {}, ID: {})",
                saved.getText(), saved.getType(), saved.getId());
        return saved;
    }

    private void createAnswerOptions(Question question, List<String> texts, List<Boolean> isCorrect) {
        for (int i = 0; i < texts.size(); i++) {
            AnswerOption option = new AnswerOption();
            option.setQuestion(question);
            option.setText(texts.get(i));
            option.setCorrect(isCorrect.get(i));
            answerOptionRepository.save(option);
        }
        logger.debug("Создано {} вариантов ответов для вопроса: {}",
                texts.size(), question.getText());
    }

    private Enrollment enrollStudent(User student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
        enrollment.setStatus("ACTIVE");
        Enrollment saved = enrollmentRepository.save(enrollment);
        logger.debug("Создана запись на курс: {} -> {} (статус: {})",
                student.getName(), course.getTitle(), saved.getStatus());
        return saved;
    }

    private Submission createSubmission(User student, Assignment assignment, String content,
                                        Integer score, String feedback) {
        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent(content);
        submission.setSubmittedAt(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
        submission.setScore(score);
        submission.setFeedback(feedback);
        Submission saved = submissionRepository.save(submission);
        logger.debug("Создано решение: {} -> {} (оценка: {})",
                student.getName(), assignment.getTitle(), saved.getScore());
        return saved;
    }

    private QuizSubmission createQuizSubmission(User student, Quiz quiz, Integer score) {
        QuizSubmission quizSubmission = new QuizSubmission();
        quizSubmission.setStudent(student);
        quizSubmission.setQuiz(quiz);
        quizSubmission.setScore(score);
        quizSubmission.setTakenAt(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
        QuizSubmission saved = quizSubmissionRepository.save(quizSubmission);
        logger.debug("Создан результат теста: {} -> {} (оценка: {})",
                student.getName(), quiz.getTitle(), saved.getScore());
        return saved;
    }

    private void createCourseReview(User student, Course course, int rating, String comment) {
        CourseReview review = new CourseReview();
        review.setStudent(student);
        review.setCourse(course);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAtReview(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
        CourseReview saved = courseReviewRepository.save(review);
        logger.debug("Создан отзыв: {} -> {} (рейтинг: {})",
                student.getName(), course.getTitle(), saved.getRating());
    }
}