package com.aman.expensemanager.service;

import com.aman.expensemanager.dto.ExpenseRequestDto;
import com.aman.expensemanager.dto.ExpenseResponseDto;
import com.aman.expensemanager.entity.Category;
import com.aman.expensemanager.entity.Expense;
import com.aman.expensemanager.entity.User;
import com.aman.expensemanager.repository.CategoryRepository;
import com.aman.expensemanager.repository.ExpenseRepository;
import com.aman.expensemanager.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // CREATE
    @Transactional
    public Expense createExpense(ExpenseRequestDto request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setUser(user);

        // ✅ CATEGORY MAPPING
        if (request.getCategoryId() != null) {
            expense.setCategory(
                    categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"))
            );
        }

        return expenseRepository.save(expense);
    }

    // READ
    @Transactional(readOnly = true)
    public Page<ExpenseResponseDto> getUserExpenses(Pageable pageable) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return expenseRepository
                .findByUserUsername(username, pageable)
                .map(this::toDto);
    }

    // UPDATE
    @Transactional
    public ExpenseResponseDto updateExpense(Long id, ExpenseRequestDto request) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        if (request.getCategoryId() != null) {
            expense.setCategory(
                    categoryRepository.findById(request.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"))
            );
        } else {
            expense.setCategory(null);
        }

        return toDto(expenseRepository.save(expense));
    }

    // DELETE
    @Transactional
    public void deleteExpense(Long id) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        expenseRepository.delete(expense);
    }

    // DTO MAPPER
    private ExpenseResponseDto toDto(Expense expense) {
        ExpenseResponseDto dto = new ExpenseResponseDto();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDescription(expense.getDescription());
        dto.setExpenseDate(expense.getExpenseDate());
        dto.setCreatedAt(expense.getCreatedAt());
        dto.setUsername(expense.getUser().getUsername());

        // ✅ CATEGORY NAME
        dto.setCategoryName(
                expense.getCategory() != null
                        ? expense.getCategory().getName()
                        : null
        );

        return dto;
    }
}