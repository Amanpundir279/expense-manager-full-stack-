package com.aman.expensemanager.repository;

import com.aman.expensemanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserUsername(String username);

    Optional<Category> findByNameAndUserUsername(String name, String username);
}