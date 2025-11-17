package org.example.unit;

import org.example.services.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.entities.Enrollment;
import org.example.repositories.EnrollmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void findAll() {
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        List<Enrollment> expectedEnrollments = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentRepository.findAll()).thenReturn(expectedEnrollments);

        List<Enrollment> result = enrollmentService.findAll();

        assertEquals(expectedEnrollments, result);
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void findById_Exists() {
        UUID id = UUID.randomUUID();
        Enrollment expectedEnrollment = new Enrollment();
        when(enrollmentRepository.findById(id)).thenReturn(Optional.of(expectedEnrollment));

        Optional<Enrollment> result = enrollmentService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedEnrollment, result.get());
        verify(enrollmentRepository, times(1)).findById(id);
    }

    @Test
    void findByStudentId() {
        UUID studentId = UUID.randomUUID();
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        List<Enrollment> expectedEnrollments = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentRepository.findByStudentId(studentId)).thenReturn(expectedEnrollments);

        List<Enrollment> result = enrollmentService.findByStudentId(studentId);

        assertEquals(expectedEnrollments, result);
        verify(enrollmentRepository, times(1)).findByStudentId(studentId);
    }

    @Test
    void findByCourseId() {
        UUID courseId = UUID.randomUUID();
        Enrollment enrollment1 = new Enrollment();
        Enrollment enrollment2 = new Enrollment();
        List<Enrollment> expectedEnrollments = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentRepository.findByCourseId(courseId)).thenReturn(expectedEnrollments);

        List<Enrollment> result = enrollmentService.findByCourseId(courseId);

        assertEquals(expectedEnrollments, result);
        verify(enrollmentRepository, times(1)).findByCourseId(courseId);
    }

    @Test
    void findByStudentAndCourse_Exists() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        Enrollment expectedEnrollment = new Enrollment();
        when(enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId))
                .thenReturn(Optional.of(expectedEnrollment));

        Optional<Enrollment> result = enrollmentService.findByStudentAndCourse(studentId, courseId);

        assertTrue(result.isPresent());
        assertEquals(expectedEnrollment, result.get());
        verify(enrollmentRepository, times(1)).findByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    void findByStudentAndCourse_NotExists() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId))
                .thenReturn(Optional.empty());

        Optional<Enrollment> result = enrollmentService.findByStudentAndCourse(studentId, courseId);

        assertFalse(result.isPresent());
        verify(enrollmentRepository, times(1)).findByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    void save() {
        Enrollment enrollmentToSave = new Enrollment();
        Enrollment savedEnrollment = new Enrollment();
        when(enrollmentRepository.save(enrollmentToSave)).thenReturn(savedEnrollment);

        Enrollment result = enrollmentService.save(enrollmentToSave);

        assertEquals(savedEnrollment, result);
        verify(enrollmentRepository, times(1)).save(enrollmentToSave);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(enrollmentRepository).deleteById(id);

        enrollmentService.deleteById(id);

        verify(enrollmentRepository, times(1)).deleteById(id);
    }
}