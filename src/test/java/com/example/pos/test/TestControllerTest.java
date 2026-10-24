package com.example.pos.test;

import com.example.pos.controller.TestController;
import com.example.pos.constant.MessageConstants;
import com.example.pos.dto.TestResponse;
import com.example.pos.network.ResponseDataPack;
import com.example.pos.service.ITestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITestService testService;

    @Test
    void returnsStandardApiResponse() throws Exception {
        org.mockito.Mockito.when(testService.findAllTests())
                .thenReturn(ResponseDataPack.success(
                        java.util.List.of(new TestResponse(1L, MessageConstants.APPLICATION_NAME, MessageConstants.APPLICATION_RUNNING)),
                        MessageConstants.TESTS_RETRIEVED_SUCCESSFULLY));

        mockMvc.perform(get("/api/v1/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value(MessageConstants.APPLICATION_NAME))
                .andExpect(jsonPath("$.status.code").value("200"))
                .andExpect(jsonPath("$.status.message").value(MessageConstants.TESTS_RETRIEVED_SUCCESSFULLY));
    }
}
