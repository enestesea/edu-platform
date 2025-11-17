package org.example.unit;

import org.example.services.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.entities.Profile;
import org.example.repositories.ProfileRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void findAll() {
        Profile profile1 = new Profile();
        Profile profile2 = new Profile();
        List<Profile> expectedProfiles = Arrays.asList(profile1, profile2);
        when(profileRepository.findAll()).thenReturn(expectedProfiles);

        List<Profile> result = profileService.findAll();

        assertEquals(expectedProfiles, result);
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void findById_Exists() {
        UUID id = UUID.randomUUID();
        Profile expectedProfile = new Profile();
        when(profileRepository.findById(id)).thenReturn(Optional.of(expectedProfile));

        Optional<Profile> result = profileService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedProfile, result.get());
        verify(profileRepository, times(1)).findById(id);
    }

    @Test
    void findById_NotExists() {
        UUID id = UUID.randomUUID();
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Profile> result = profileService.findById(id);

        assertFalse(result.isPresent());
        verify(profileRepository, times(1)).findById(id);
    }

    @Test
    void save() {
        Profile profileToSave = new Profile();
        Profile savedProfile = new Profile();
        when(profileRepository.save(profileToSave)).thenReturn(savedProfile);

        Profile result = profileService.save(profileToSave);

        assertEquals(savedProfile, result);
        verify(profileRepository, times(1)).save(profileToSave);
    }

    @Test
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(profileRepository).deleteById(id);

        profileService.deleteById(id);

        verify(profileRepository, times(1)).deleteById(id);
    }
}