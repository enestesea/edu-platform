package org.example.integration;

import org.example.entities.Course;
import org.example.entities.Module;
import org.example.entities.User;
import org.example.entities.UserRole;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.persistence.EntityManager; // ← ЗАМЕНИТЬ javax.persistence на jakarta.persistence
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LazyLoadTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private org.example.repositories.CourseRepository courseRepository;

    @Autowired
    private org.example.repositories.UserRepository userRepository;

    @Autowired
    private org.example.repositories.CategoryRepository categoryRepository;

    @Test
    @org.springframework.transaction.annotation.Transactional
    void testLazyLoading_WithinTransaction_Success() {
        //Ленивая загрузка внутри транзакции работает
        User teacher = createUser("Lazy Test Teacher", "lazy@test.com", UserRole.TEACHER);
        org.example.entities.Category category = createCategory("Test Category");

        Course course = new Course();
        course.setTitle("Lazy Loading Course");
        course.setTeacher(teacher);
        course.setCategory(category);
        course = courseRepository.save(course);

        Course foundCourse = courseRepository.findById(course.getId()).orElseThrow();

        assertDoesNotThrow(() -> {
            List<Module> modules = foundCourse.getModules();
            assertNotNull(modules);
            assertTrue(modules.isEmpty());
        });
    }

    @Test
    void testLazyLoading_OutsideTransaction_ThrowsException() {
        //Ленивая загрузка вне транзакции - ожидается LazyInitializationException
        User teacher = createUser("Lazy Test Teacher 2", "lazy2@test.com", UserRole.TEACHER);
        org.example.entities.Category category = createCategory("Test Category 2");

        Course course = new Course();
        course.setTitle("Lazy Loading Course 2");
        course.setTeacher(teacher);
        course.setCategory(category);
        course = courseRepository.save(course);

        Course foundCourse = loadCourseInSeparateTransaction(course.getId());

        assertThrows(LazyInitializationException.class, () -> {
            List<Module> modules = foundCourse.getModules();
            modules.size(); // попытка доступа к данным
        });
    }

    @Test
    @org.springframework.transaction.annotation.Transactional
    void testLazyLoading_WithJoinFetch_Solution() {
        // Решение проблемы ленивой загрузки через JOIN FETCH
        User teacher = createUser("Fetch Test Teacher", "fetch@test.com", UserRole.TEACHER);
        org.example.entities.Category category = createCategory("Fetch Category");

        Course course = new Course();
        course.setTitle("Fetch Course");
        course.setTeacher(teacher);
        course.setCategory(category);
        course = courseRepository.save(course);

        Course courseWithModules = entityManager.createQuery(
                        "SELECT c FROM Course c LEFT JOIN FETCH c.modules WHERE c.id = :courseId", Course.class)
                .setParameter("courseId", course.getId())
                .getSingleResult();

        assertNotNull(courseWithModules);
        assertDoesNotThrow(() -> {
            List<Module> modules = courseWithModules.getModules();
            assertNotNull(modules);
        });
    }

    private Course loadCourseInSeparateTransaction(UUID courseId) {
        return courseRepository.findById(courseId).orElseThrow();
    }

    private User createUser(String name, String email, UserRole role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        return userRepository.save(user);
    }

    private org.example.entities.Category createCategory(String name) {
        org.example.entities.Category category = new org.example.entities.Category();
        category.setName(name);
        return categoryRepository.save(category);
    }
}