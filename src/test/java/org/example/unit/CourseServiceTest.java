package org.example.unit;

import org.example.services.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.entities.Course;
import org.example.repositories.CourseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void findAll() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findAll()).thenReturn(expectedCourses);

        List<Course> result = courseService.findAll();

        assertEquals(expectedCourses, result);
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void findById_Exists() {
        UUID id = UUID.randomUUID();
        Course expectedCourse = new Course();
        when(courseRepository.findById(id)).thenReturn(Optional.of(expectedCourse));

        Optional<Course> result = courseService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedCourse, result.get());
        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    void findByTeacherId() {
        UUID teacherId = UUID.randomUUID();
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findByTeacherId(teacherId)).thenReturn(expectedCourses);

        List<Course> result = courseService.findByTeacherId(teacherId);

        assertEquals(expectedCourses, result);
        verify(courseRepository, times(1)).findByTeacherId(teacherId);
    }

    @Test
    void findByCategoryId() {
        UUID categoryId = UUID.randomUUID();
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findByCategoryId(categoryId)).thenReturn(expectedCourses);

        List<Course> result = courseService.findByCategoryId(categoryId);

        assertEquals(expectedCourses, result);
        verify(courseRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void findByCategoryName() {
        String categoryName = "Programming";
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> expectedCourses = Arrays.asList(course1, course2);
        when(courseRepository.findByCategory_Name(categoryName)).thenReturn(expectedCourses);

        List<Course> result = courseService.findByCategoryName(categoryName);

        assertEquals(expectedCourses, result);
        verify(courseRepository, times(1)).findByCategory_Name(categoryName);
    }

    @Test
    void save() {
        Course courseToSave = new Course();
        Course savedCourse = new Course();
        when(courseRepository.save(courseToSave)).thenReturn(savedCourse);

        Course result = courseService.save(courseToSave);

        assertEquals(savedCourse, result);
        verify(courseRepository, times(1)).save(courseToSave);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(courseRepository).deleteById(id);

        courseService.deleteById(id);

        verify(courseRepository, times(1)).deleteById(id);
    }
}
