package com.aman.expensemanager.controller;

import com.aman.expensemanager.dto.CategoryResponseDto;
import com.aman.expensemanager.entity.Category;
import com.aman.expensemanager.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.ok(
                categoryService.createCategory(category.getName())
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getUserCategories() {
        return ResponseEntity.ok(categoryService.getUserCategories());
    }
}