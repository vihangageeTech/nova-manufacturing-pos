package com.example.pos.controller;

import com.example.pos.constant.EndpointConstants;
import com.example.pos.constant.MessageConstants;
import com.example.pos.dto.TestResponse;
import com.example.pos.network.ApiResponseWrapper;
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
@RequestMapping(EndpointConstants.V1 + EndpointConstants.TEST)
@Tag(
        name = "Test",
        description = "APIs for managing Test resources"
)
public interface ITestController {

    @Operation(
            summary = "Get all Tests",
            description = "Retrieves all available Test resources."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = MessageConstants.TESTS_RETRIEVED_SUCCESSFULLY
            )
    })
    @GetMapping
    ResponseEntity<ApiResponseWrapper<List<TestResponse>>> findAllTests();

    @GetMapping("/{id}")
    ResponseEntity<ApiResponseWrapper<TestResponse>> findTest(@PathVariable Long id);

    @PostMapping
    ResponseEntity<ApiResponseWrapper<TestResponse>> createTest(@Valid @RequestBody TestResponse request);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponseWrapper<TestResponse>> updateTest(
            @PathVariable Long id,
            @Valid @RequestBody TestResponse request
    );

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponseWrapper<TestResponse>> deleteTest(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<ApiResponseWrapper<List<TestResponse>>> searchTests(
            @RequestParam String category,
            @RequestParam String status
    );
}
