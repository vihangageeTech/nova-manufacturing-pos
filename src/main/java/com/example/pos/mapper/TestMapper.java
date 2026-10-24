package com.example.pos.mapper;

import com.example.pos.dto.TestResponse;
import com.example.pos.entity.Test;
import org.springframework.stereotype.Component;

/** Maps between the Test persistence entity and its API DTO. */
@Component
public class TestMapper {

    public TestResponse toDto(Test entity) {
        if (entity == null) {
            return null;
        }

        return new TestResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getStatus(),
                entity.getPriority()
        );
    }

    public Test toDao(TestResponse dto) {
        if (dto == null) {
            return null;
        }

        Test entity = new Test();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setStatus(dto.getStatus());
        entity.setPriority(dto.getPriority());
        return entity;
    }

    public void updateDao(TestResponse dto, Test entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setStatus(dto.getStatus());
        entity.setPriority(dto.getPriority());
    }
}
