package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Profile;
import org.example.services.ProfileService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable UUID id) {
        Optional<Profile> profile = profileService.findById(id);
        return profile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        return profileService.save(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable UUID id, @RequestBody Profile profile) {
        if (!profileService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        profile.setId(id);
        return ResponseEntity.ok(profileService.save(profile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        if (!profileService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        profileService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}