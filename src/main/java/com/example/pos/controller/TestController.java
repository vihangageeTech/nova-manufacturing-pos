package com.example.pos.controller;

import com.example.pos.dto.TestResponse;
import com.example.pos.network.ApiResponseWrapper;
import com.example.pos.network.NetworkUtils;
import com.example.pos.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController implements ITestController {


    @Autowired
    private ITestService testService;

    @Override
    public ResponseEntity<ApiResponseWrapper<List<TestResponse>>> findAllTests() {
        return NetworkUtils.wrap(testService.findAllTests());
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<TestResponse>> findTest(Long id) {
        return NetworkUtils.wrap(testService.findTest(id));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<TestResponse>> createTest(TestResponse request) {
        return NetworkUtils.wrap(testService.createTest(request));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<TestResponse>> updateTest(Long id, TestResponse request) {
        return NetworkUtils.wrap(testService.updateTest(id, request));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<TestResponse>> deleteTest(Long id) {
        return NetworkUtils.wrap(testService.deleteTest(id));
    }

    @Override
    public ResponseEntity<ApiResponseWrapper<List<TestResponse>>> searchTests(String category, String status) {
        return NetworkUtils.wrap(testService.searchTest(category, status));
    }
}
