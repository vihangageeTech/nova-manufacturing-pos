package com.example.pos.controller;

import com.example.pos.constant.EndpointConstants;
import com.example.pos.constant.MessageConstants;
import com.example.pos.dto.ProductRequestDto;
import com.example.pos.dto.ProductResponseDto;
import com.example.pos.network.ApiResponseWrapper;

import java.math.BigDecimal;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointConstants.V1 + EndpointConstants.PRODUCT)
@Tag(
        name = "Product",
        description = "APIs for managing Product resources"
)
public interface IProductController {

    @Operation(
            summary = "Get all Products",
            description = "Retrieves all available Product resources."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = MessageConstants.PRODUCTS_RETRIEVED_SUCCESSFULLY
            )
    })
    @GetMapping
    ResponseEntity<ApiResponseWrapper<List<ProductResponseDto>>> findAllProducts();

    @GetMapping("/{id}")
    ResponseEntity<ApiResponseWrapper<ProductResponseDto>> findProduct(@PathVariable Long id);

    @PostMapping
    ResponseEntity<ApiResponseWrapper<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto request);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponseWrapper<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto request
    );

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponseWrapper<ProductResponseDto>> deleteProduct(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<ApiResponseWrapper<List<ProductResponseDto>>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long categoryId
    );
}