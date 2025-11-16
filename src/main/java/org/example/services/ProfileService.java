package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Profile;
import org.example.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findById(UUID id) {
        return profileRepository.findById(id);
    }

    public Profile save(Profile Profile) {
        return profileRepository.save(Profile);
    }

    public void deleteById(UUID id) {
        profileRepository.deleteById(id);
    }
}