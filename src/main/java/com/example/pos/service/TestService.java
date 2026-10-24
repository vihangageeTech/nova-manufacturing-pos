package com.example.pos.service;

import com.example.pos.dto.TestResponse;
import com.example.pos.constant.MessageConstants;
import com.example.pos.network.ResponseData;
import com.example.pos.network.ResponseDataPack;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** Service facade that applies the common error-handling response pattern. */
@Service
public class TestService implements ITestService {

    private final TestServiceImpl testServiceImpl;

    public TestService(TestServiceImpl testServiceImpl) {
        this.testServiceImpl = testServiceImpl;
    }

    @Override
    public ResponseDataPack<TestResponse> findAllTests() {
        try {
            return testServiceImpl.findAll();
        } catch (Exception exception) {
            return ResponseDataPack.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_RETRIEVE_TESTS);
        }
    }


    @Override
    public ResponseDataPack<TestResponse> searchTest(String category, String status) {
        try {
            return testServiceImpl.searchTest(category, status);
        } catch (Exception exception) {
            return ResponseDataPack.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_RETRIEVE_TESTS);
        }
    }

    @Override
    public ResponseData<TestResponse> findTest(Long id) {
        try {
            return testServiceImpl.findTest(id);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_RETRIEVE_TEST);
        }
    }

    @Override
    public ResponseData<TestResponse> createTest(TestResponse request) {
        try {
            return testServiceImpl.createTest(request);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_CREATE_TEST);
        }
    }

    @Override
    public ResponseData<TestResponse> updateTest(Long id, TestResponse request) {
        try {
            return testServiceImpl.updateTest(id, request);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_UPDATE_TEST);
        }
    }

    @Override
    public ResponseData<TestResponse> deleteTest(Long id) {
        try {
            return testServiceImpl.deleteTest(id);
        } catch (Exception exception) {
            return ResponseData.error(HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.UNABLE_TO_DELETE_TEST);
        }
    }
}
