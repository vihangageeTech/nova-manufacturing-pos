package com.example.pos.service;

import com.example.pos.dto.ProductRequestDto;
import com.example.pos.dto.ProductResponseDto;
import com.example.pos.constant.MessageConstants;
import com.example.pos.network.ResponseData;
import com.example.pos.network.ResponseDataPack;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService implements IProductService {

    private final ProductServiceImpl productServiceImpl;

    public ProductService(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @Override
    public ResponseDataPack<ProductResponseDto> findAllProducts() {
        try {
            return productServiceImpl.findAll();
        } catch (Exception exception) {
            return ResponseDataPack.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_RETRIEVE_PRODUCTS);
        }
    }

    @Override
    public ResponseDataPack<ProductResponseDto> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId) {
        try {
            return productServiceImpl.searchProducts(name, minPrice, maxPrice, categoryId);
        } catch (Exception exception) {
            return ResponseDataPack.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_RETRIEVE_PRODUCTS);
        }
    }

    @Override
    public ResponseData<ProductResponseDto> findProduct(Long id) {
        try {
            return productServiceImpl.findProduct(id);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_RETRIEVE_PRODUCT);
        }
    }

    @Override
    public ResponseData<ProductResponseDto> createProduct(ProductRequestDto request) {
        try {
            return productServiceImpl.createProduct(request);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_CREATE_PRODUCT);
        }
    }

    @Override
    public ResponseData<ProductResponseDto> updateProduct(Long id, ProductRequestDto request) {
        try {
            return productServiceImpl.updateProduct(id, request);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_UPDATE_PRODUCT);
        }
    }

    @Override
    public ResponseData<ProductResponseDto> deleteProduct(Long id) {
        try {
            return productServiceImpl.deleteProduct(id);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_DELETE_PRODUCT);
        }
    }
}