package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
    List<Module> findByCourseId(UUID courseId);
}

