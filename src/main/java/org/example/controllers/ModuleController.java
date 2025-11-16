package org.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entities.Module;
import org.example.services.ModuleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<Module> getAllModules() {
        return moduleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable UUID id) {
        Optional<Module> module = moduleService.findById(id);
        return module.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<Module> getModulesByCourse(@PathVariable UUID courseId) {
        return moduleService.findByCourseId(courseId);
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        return moduleService.save(module);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable UUID id, @RequestBody Module module) {
        if (!moduleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        module.setId(id);
        return ResponseEntity.ok(moduleService.save(module));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable UUID id) {
        if (!moduleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        moduleService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}