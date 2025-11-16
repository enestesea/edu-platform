package org.example.services;

import org.springframework.stereotype.Service;
import org.example.entities.Module;
import org.example.repositories.ModuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    public Optional<Module> findById(UUID id) {
        return moduleRepository.findById(id);
    }

    public List<Module> findByCourseId(UUID courseId) {
        return moduleRepository.findByCourseId(courseId);
    }

    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    public void deleteById(UUID id) {
        moduleRepository.deleteById(id);
    }
}