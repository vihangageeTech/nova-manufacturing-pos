package com.example.pos.service;

import com.example.pos.dto.TestResponse;
import com.example.pos.network.ResponseData;
import com.example.pos.network.ResponseDataPack;


public interface ITestService {

    ResponseDataPack<TestResponse> findAllTests();

    ResponseDataPack<TestResponse> searchTest(String category, String status);

    ResponseData<TestResponse> findTest(Long id);

    ResponseData<TestResponse> createTest(TestResponse request);

    ResponseData<TestResponse> updateTest(Long id, TestResponse request);

    ResponseData<TestResponse> deleteTest(Long id);
}
