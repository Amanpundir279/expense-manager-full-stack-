package com.aman.expensemanager.service;

import com.aman.expensemanager.dto.CategoryResponseDto;
import com.aman.expensemanager.entity.Category;
import com.aman.expensemanager.entity.User;
import com.aman.expensemanager.repository.CategoryRepository;
import com.aman.expensemanager.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Category createCategory(String name) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        categoryRepository
                .findByNameAndUserUsername(name, username)
                .ifPresent(c -> {
                    throw new RuntimeException("Category already exists");
                });

        Category category = new Category();
        category.setName(name);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getUserCategories() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return categoryRepository.findByUserUsername(username)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private CategoryResponseDto toDto(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}