package com.example.pos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Product code is mandatory")
    private String productCode;

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    @NotNull(message = "Unit price is mandatory")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    private BigDecimal unitPrice;

    @NotNull(message = "Available quantity is mandatory")
    @Min(value = 0, message = "Available quantity must be zero or greater")
    private Integer availableQuantity;

    @NotNull(message = "Category ID is mandatory")
    private Long categoryId;

    @NotNull(message = "Active status is mandatory")
    private Boolean active;
}