package com.example.pos.controller;

import com.example.pos.constant.EndpointConstants;
import com.example.pos.dto.OrderRequestDto;
import com.example.pos.entity.order.Order;
import com.example.pos.network.ApiResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(EndpointConstants.V1 + EndpointConstants.ORDERS)
public interface IOrderController {

    @PostMapping
    ResponseEntity<ApiResponseWrapper<Order>> placeOrder(@RequestBody OrderRequestDto request);
}