package com.example.pos.service;

import com.example.pos.dto.ProductRequestDto;
import com.example.pos.dto.ProductResponseDto;
import com.example.pos.network.ResponseData;
import com.example.pos.network.ResponseDataPack;

import java.math.BigDecimal;

public interface IProductService {

    ResponseDataPack<ProductResponseDto> findAllProducts();

    ResponseDataPack<ProductResponseDto> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId);

    ResponseData<ProductResponseDto> findProduct(Long id);

    ResponseData<ProductResponseDto> createProduct(ProductRequestDto request);

    ResponseData<ProductResponseDto> updateProduct(Long id, ProductRequestDto request);

    ResponseData<ProductResponseDto> deleteProduct(Long id);
}