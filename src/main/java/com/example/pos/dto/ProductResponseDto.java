package com.example.pos.dto;

import com.example.pos.entity.TestStatus;
import com.example.pos.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Schema(
        name = "ProductResponse",
        description = "Product response resource"
)
public class ProductResponseDto {

    public ProductResponseDto(Long productId, String productName, String productCode) {
        this.productId = productId;
        this.categoryName = productName;
        this.productCode = productCode;
    }

    public ProductResponseDto(Long productId, String productCode, String productName,
                              BigDecimal unitPrice, Integer availableQuantity,
                              Boolean active, Long categoryId, String categoryName) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
        this.active = active;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Schema(
            description = "Product unique identifier",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long productId;

    @Schema(
            description = "Mandatory unique product business code",
            example = "PRD001"
    )
    private String productCode;

    @Schema(
            description = "Mandatory product display name",
            example = "Notebook A5"
    )
    private String productName;

    @Schema(
            description = "Current price in LKR; greater than zero",
            example = "350.00"
    )
    private BigDecimal unitPrice;

    @Schema(
            description = "Current stock in whole units; zero or greater",
            example = "100"
    )
    private Integer availableQuantity;

    @Schema(
            description = "Whether this product may be sold",
            example = "true"
    )
    private Boolean active;

    @Schema(
            description = "Category identification for this product",
            example = "1"
    )
    private Long categoryId;

    @Schema(
            description = "Mandatory Category display name",
            example = "Stationery"
    )
    private String categoryName;


}