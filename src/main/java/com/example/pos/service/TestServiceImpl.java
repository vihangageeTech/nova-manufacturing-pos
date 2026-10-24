package com.example.pos.service;

import com.example.pos.dto.TestResponse;
import com.example.pos.constant.MessageConstants;
import com.example.pos.entity.Test;
import com.example.pos.mapper.TestMapper;
import com.example.pos.network.ResponseData;
import com.example.pos.network.ResponseDataPack;
import com.example.pos.repository.ITestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Repository-facing implementation. Business data operations stay here. */
@Service
@Transactional
public class TestServiceImpl {

    private final ITestRepository testRepository;
    private final TestMapper testMapper;

    public TestServiceImpl(ITestRepository testRepository, TestMapper testMapper) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
    }

    @Transactional(readOnly = true)
    public ResponseDataPack<TestResponse> findAll() {
        List<TestResponse> tests = testRepository.findAll().stream()
                .map(testMapper::toDto)
                .toList();
        return ResponseDataPack.success(tests, MessageConstants.TESTS_SEARCHED_SUCCESSFULLY);
    }


    @Transactional(readOnly = true)
    public ResponseDataPack<TestResponse> searchTest(String category, String status) {
        List<TestResponse> tests = testRepository.searchTest(category, status).stream()
                .map(testMapper::toDto)
                .toList();
        return ResponseDataPack.success(tests, MessageConstants.TESTS_RETRIEVED_SUCCESSFULLY);
    }

    @Transactional(readOnly = true)
    public ResponseData<TestResponse> findTest(Long id) {
        return testRepository.findById(id)
                .map(test -> ResponseData.success(testMapper.toDto(test), MessageConstants.TEST_RETRIEVED_SUCCESSFULLY))
                .orElseGet(() -> ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.TEST_NOT_FOUND));
    }

    public ResponseData<TestResponse> createTest(TestResponse request) {
        Test test = testMapper.toDao(request);
        return ResponseData.created(testMapper.toDto(testRepository.save(test)), MessageConstants.TEST_CREATED_SUCCESSFULLY);
    }

    public ResponseData<TestResponse> updateTest(Long id, TestResponse request) {
        return testRepository.findById(id)
                .map(test -> {
                    testMapper.updateDao(request, test);
                    return ResponseData.success(testMapper.toDto(testRepository.save(test)), MessageConstants.TEST_UPDATED_SUCCESSFULLY);
                })
                .orElseGet(() -> ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.TEST_NOT_FOUND));
    }

    public ResponseData<TestResponse> deleteTest(Long id) {
        return testRepository.findById(id)
                .map(test -> {
                    TestResponse response = testMapper.toDto(test);
                    testRepository.delete(test);
                    return ResponseData.success(response, MessageConstants.TEST_DELETED_SUCCESSFULLY);
                })
                .orElseGet(() -> ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.TEST_NOT_FOUND));
    }

}
