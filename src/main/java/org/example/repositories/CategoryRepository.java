package org.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.entities.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
}

