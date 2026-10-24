package com.example.pos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.example.pos.entity.TestStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(
        name = "Test",
        description = "Test resource"
)
public class TestResponse {

    public TestResponse() {
    }

    public TestResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TestResponse(Long id, String code, String name, String description,
                        String category, TestStatus status, Integer priority) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.priority = priority;
    }

    @Schema(
            description = "Unique identifier",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    private String code;

    @Schema(
            description = "Name of the test",
            example = "Test name"
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Description of the test",
            example = "Test description"
    )
    private String description;

    private String category;

    private TestStatus status;

    private Integer priority;
}
