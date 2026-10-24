package com.example.pos.controller;

import com.example.pos.dto.ProductRequestDto;
import com.example.pos.dto.ProductResponseDto;
import com.example.pos.network.ApiResponseWrapper;
import com.example.pos.network.NetworkUtils;
import com.example.pos.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController implements IProductController {

    @Autowired
    private IProductService productService;

    @Override
    public ResponseEntity<ApiResponseWrapper<List<ProductResponseDto>>> findAllProducts() {
        return NetworkUtils.wrap(productService.findAllProducts());
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<ProductResponseDto>> findProduct(Long id) {
        return NetworkUtils.wrap(productService.findProduct(id));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<ProductResponseDto>> createProduct(ProductRequestDto request) {
        return NetworkUtils.wrap(productService.createProduct(request));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<ProductResponseDto>> updateProduct(Long id, ProductRequestDto request) {
        return NetworkUtils.wrap(productService.updateProduct(id, request));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<ProductResponseDto>> deleteProduct(Long id) {
        return NetworkUtils.wrap(productService.deleteProduct(id));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<List<ProductResponseDto>>> searchProducts(
            String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId) {
        return NetworkUtils.wrap(productService.searchProducts(name, minPrice, maxPrice, categoryId));
    }
}