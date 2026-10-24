package com.example.pos.repository;

import com.example.pos.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    @Query(value = "SELECT * FROM product "
            + "WHERE (:name IS NULL OR LOWER(product_name) LIKE LOWER(CONCAT('%', :name, '%'))) "
            + "AND (:minPrice IS NULL OR unit_price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR unit_price <= :maxPrice) "
            + "AND (:categoryId IS NULL OR category_id = :categoryId) "
            + "ORDER BY product_name ASC", nativeQuery = true)
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("categoryId") Long categoryId
    );
}