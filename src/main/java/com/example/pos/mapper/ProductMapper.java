package com.example.pos.mapper;

import com.example.pos.dto.ProductRequestDto;
import com.example.pos.dto.ProductResponseDto;
import com.example.pos.entity.category.Category;
import com.example.pos.entity.product.Product;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final EntityManager entityManager;

    public ProductMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ProductResponseDto toDto(Product entity) {
        if (entity == null) {
            return null;
        }

        Long categoryId = null;
        String categoryName = null;
        if (entity.getCategory() != null) {
            categoryId = entity.getCategory().getId();
            categoryName = entity.getCategory().getCategoryName();
        }

        return new ProductResponseDto(
                entity.getProductId(),
                entity.getProductCode(),
                entity.getProductName(),
                entity.getUnitPrice(),
                entity.getAvailableQuantity(),
                entity.getActive(),
                categoryId,
                categoryName
        );
    }

    public Product toDao(ProductRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Product entity = new Product();
        entity.setProductCode(dto.getProductCode());
        entity.setProductName(dto.getProductName());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setAvailableQuantity(dto.getAvailableQuantity());
        entity.setActive(dto.getActive());

        if (dto.getCategoryId() != null) {
            Category category = entityManager.getReference(Category.class, dto.getCategoryId());
            entity.setCategory(category);
        }

        return entity;
    }

    public void updateDao(ProductRequestDto dto, Product entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setProductCode(dto.getProductCode());
        entity.setProductName(dto.getProductName());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setAvailableQuantity(dto.getAvailableQuantity());
        entity.setActive(dto.getActive());

        if (dto.getCategoryId() != null) {
            Category category = entityManager.getReference(Category.class, dto.getCategoryId());
            entity.setCategory(category);
        }
    }
}