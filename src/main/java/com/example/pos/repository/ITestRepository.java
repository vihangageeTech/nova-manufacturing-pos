package com.example.pos.repository;

import com.example.pos.entity.Test;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITestRepository extends JpaRepository<Test, Long> {

    /** Sample native SQL query. */
    @Query(value = "SELECT * FROM test "
            + "WHERE category = :category AND status = :status "
            + "ORDER BY priority DESC, created_at DESC", nativeQuery = true)
    List<Test> searchTest(
            @Param("category") String category,
            @Param("status") String status
    );
}
