package org.example.unit;

import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.entities.User;
import org.example.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll_ShouldReturnAllUsers() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setEmail("user2@example.com");

        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAll();
        
        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        UUID userId = UUID.randomUUID();
        User expectedUser = new User();
        expectedUser.setId(userId);
        expectedUser.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        
        Optional<User> result = userService.findById(userId);
        
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findById_WhenUserNotExists_ShouldReturnEmpty() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setId(UUID.randomUUID());
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
        
        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByEmail_WhenUserNotExists_ShouldReturnEmpty() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        
        Optional<User> result = userService.findByEmail(email);
        
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void save_ShouldReturnSavedUser() {
        User userToSave = new User();
        userToSave.setEmail("newuser@example.com");

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setEmail("newuser@example.com");

        when(userRepository.save(userToSave)).thenReturn(savedUser);
        
        User result = userService.save(userToSave);

        assertNotNull(result);
        assertEquals(savedUser, result);
        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        UUID userId = UUID.randomUUID();
        
        userService.deleteById(userId);
        
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteById_WithValidId_ShouldDeleteUser() {
        UUID userId = UUID.randomUUID();
        doNothing().when(userRepository).deleteById(userId);
        
        userService.deleteById(userId);
        
        verify(userRepository, times(1)).deleteById(userId);
        verifyNoMoreInteractions(userRepository);
    }
}