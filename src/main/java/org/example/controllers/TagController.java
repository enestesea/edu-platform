package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Tag;
import org.example.services.TagService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable UUID id) {
        Optional<Tag> tag = tagService.findById(id);
        return tag.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable UUID id, @RequestBody Tag tag) {
        if (!tagService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tag.setId(id);
        return ResponseEntity.ok(tagService.save(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        if (!tagService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tagService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}