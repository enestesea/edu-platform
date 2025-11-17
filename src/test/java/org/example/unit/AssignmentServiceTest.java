package org.example.unit;

import org.example.services.AssignmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.entities.Assignment;
import org.example.repositories.AssignmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void findAll() {
        Assignment assignment1 = new Assignment();
        Assignment assignment2 = new Assignment();
        List<Assignment> expectedAssignments = Arrays.asList(assignment1, assignment2);
        when(assignmentRepository.findAll()).thenReturn(expectedAssignments);

        List<Assignment> result = assignmentService.findAll();

        assertEquals(expectedAssignments, result);
        verify(assignmentRepository, times(1)).findAll();
    }

    @Test
    void findById_Exists() {
        UUID id = UUID.randomUUID();
        Assignment expectedAssignment = new Assignment();
        when(assignmentRepository.findById(id)).thenReturn(Optional.of(expectedAssignment));

        Optional<Assignment> result = assignmentService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedAssignment, result.get());
        verify(assignmentRepository, times(1)).findById(id);
    }

    @Test
    void findByLessonId() {
        UUID lessonId = UUID.randomUUID();
        Assignment assignment1 = new Assignment();
        Assignment assignment2 = new Assignment();
        List<Assignment> expectedAssignments = Arrays.asList(assignment1, assignment2);
        when(assignmentRepository.findByLessonId(lessonId)).thenReturn(expectedAssignments);

        List<Assignment> result = assignmentService.findByLessonId(lessonId);

        assertEquals(expectedAssignments, result);
        verify(assignmentRepository, times(1)).findByLessonId(lessonId);
    }

    @Test
    void save() {
        Assignment assignmentToSave = new Assignment();
        Assignment savedAssignment = new Assignment();
        when(assignmentRepository.save(assignmentToSave)).thenReturn(savedAssignment);

        Assignment result = assignmentService.save(assignmentToSave);

        assertEquals(savedAssignment, result);
        verify(assignmentRepository, times(1)).save(assignmentToSave);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(assignmentRepository).deleteById(id);

        assignmentService.deleteById(id);

        verify(assignmentRepository, times(1)).deleteById(id);
    }
}