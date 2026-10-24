package com.example.pos.service;

import com.example.pos.dto.ProductRequestDto;
import com.example.pos.dto.ProductResponseDto;
import com.example.pos.constant.MessageConstants;
import com.example.pos.entity.category.Category;
import com.example.pos.entity.product.Product;
import com.example.pos.mapper.ProductMapper;
import com.example.pos.network.ResponseData;
import com.example.pos.network.ResponseDataPack;
import com.example.pos.repository.IProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;
    private final EntityManager entityManager;

    public ProductServiceImpl(IProductRepository productRepository, ProductMapper productMapper, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public ResponseDataPack<ProductResponseDto> findAll() {
        List<ProductResponseDto> products = productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
        return ResponseDataPack.success(products, MessageConstants.PRODUCTS_SEARCHED_SUCCESSFULLY);
    }

    @Transactional(readOnly = true)
    public ResponseDataPack<ProductResponseDto> searchProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Long categoryId) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            return ResponseDataPack.error(HttpStatus.BAD_REQUEST, "Minimum price cannot be greater than maximum price");
        }

        List<ProductResponseDto> products = productRepository.searchProducts(name, minPrice, maxPrice, categoryId).stream()
                .map(productMapper::toDto)
                .toList();
        return ResponseDataPack.success(products, MessageConstants.PRODUCTS_RETRIEVED_SUCCESSFULLY);
    }

    @Transactional(readOnly = true)
    public ResponseData<ProductResponseDto> findProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> ResponseData.success(productMapper.toDto(product), MessageConstants.PRODUCT_RETRIEVED_SUCCESSFULLY))
                .orElseGet(() -> ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));
    }

    public ResponseData<ProductResponseDto> createProduct(ProductRequestDto request) {
        if (request.getCategoryId() != null) {
            Category category = entityManager.find(Category.class, request.getCategoryId());
            if (category != null && Boolean.FALSE.equals(category.getActive())) {
                return ResponseData.error(HttpStatus.BAD_REQUEST, "Rejected because category is inactive");
            }
        }

        Product product = productMapper.toDao(request);
        return ResponseData.created(productMapper.toDto(productRepository.save(product)), MessageConstants.PRODUCT_CREATED_SUCCESSFULLY);
    }

    public ResponseData<ProductResponseDto> updateProduct(Long id, ProductRequestDto request) {
        return productRepository.findById(id)
                .map(product -> {
                    productMapper.updateDao(request, product);
                    return ResponseData.success(productMapper.toDto(productRepository.save(product)), MessageConstants.PRODUCT_UPDATED_SUCCESSFULLY);
                })
                .orElseGet(() -> ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));
    }

    public ResponseData<ProductResponseDto> deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductResponseDto response = productMapper.toDto(product);
                    productRepository.delete(product);
                    return ResponseData.success(response, MessageConstants.PRODUCT_DELETED_SUCCESSFULLY);
                })
                .orElseGet(() -> ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND));
    }
}