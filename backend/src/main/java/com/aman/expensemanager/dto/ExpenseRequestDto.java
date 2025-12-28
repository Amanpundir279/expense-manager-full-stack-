package com.aman.expensemanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequestDto {

    @NotNull
    private BigDecimal amount;

    private String description;

    @NotNull
    private LocalDate expenseDate;

    // OPTIONAL (only if category is supported)
    private Long categoryId;
}