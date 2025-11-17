package org.example.unit;

import org.example.services.QuizService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.entities.Quiz;
import org.example.repositories.QuizRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @Test
    void findAll() {
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();
        List<Quiz> expectedQuizzes = Arrays.asList(quiz1, quiz2);
        when(quizRepository.findAll()).thenReturn(expectedQuizzes);

        List<Quiz> result = quizService.findAll();

        assertEquals(expectedQuizzes, result);
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void findById_Exists() {
        UUID id = UUID.randomUUID();
        Quiz expectedQuiz = new Quiz();
        when(quizRepository.findById(id)).thenReturn(Optional.of(expectedQuiz));

        Optional<Quiz> result = quizService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedQuiz, result.get());
        verify(quizRepository, times(1)).findById(id);
    }

    @Test
    void findByModuleId_Exists() {
        UUID moduleId = UUID.randomUUID();
        Quiz expectedQuiz = new Quiz();
        when(quizRepository.findByModuleId(moduleId)).thenReturn(Optional.of(expectedQuiz));

        Optional<Quiz> result = quizService.findByModuleId(moduleId);

        assertTrue(result.isPresent());
        assertEquals(expectedQuiz, result.get());
        verify(quizRepository, times(1)).findByModuleId(moduleId);
    }

    @Test
    void findByModuleId_NotExists() {
        UUID moduleId = UUID.randomUUID();
        when(quizRepository.findByModuleId(moduleId)).thenReturn(Optional.empty());

        Optional<Quiz> result = quizService.findByModuleId(moduleId);

        assertFalse(result.isPresent());
        verify(quizRepository, times(1)).findByModuleId(moduleId);
    }

    @Test
    void save() {
        Quiz quizToSave = new Quiz();
        Quiz savedQuiz = new Quiz();
        when(quizRepository.save(quizToSave)).thenReturn(savedQuiz);

        Quiz result = quizService.save(quizToSave);

        assertEquals(savedQuiz, result);
        verify(quizRepository, times(1)).save(quizToSave);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(quizRepository).deleteById(id);

        quizService.deleteById(id);

        verify(quizRepository, times(1)).deleteById(id);
    }
}