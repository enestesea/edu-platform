package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Tag;
import org.example.repositories.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findById(UUID id) {
        return tagRepository.findById(id);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteById(UUID id) {
        tagRepository.deleteById(id);
    }
}