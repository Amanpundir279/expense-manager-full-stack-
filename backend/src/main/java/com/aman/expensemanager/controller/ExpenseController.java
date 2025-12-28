package com.aman.expensemanager.controller;

import com.aman.expensemanager.dto.ExpenseRequestDto;
import com.aman.expensemanager.dto.ExpenseResponseDto;
import com.aman.expensemanager.entity.Expense;
import com.aman.expensemanager.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ✅ CREATE EXPENSE
    @PostMapping
    public Expense createExpense(@RequestBody ExpenseRequestDto request) {
        return expenseService.createExpense(request);
    }

    // ✅ GET EXPENSES WITH PAGINATION (logged-in user)
    @GetMapping
    public Page<ExpenseResponseDto> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseService.getUserExpenses(pageable);
    }
    @PutMapping("/{id}")
    public ExpenseResponseDto updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequestDto dto) {
        return expenseService.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}