package com.example.pos.controller;

import com.example.pos.dto.OrderRequestDto;
import com.example.pos.entity.order.Order;
import com.example.pos.network.ApiResponseWrapper;
import com.example.pos.network.NetworkUtils;
import com.example.pos.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements IOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public ResponseEntity<ApiResponseWrapper<Order>> placeOrder(OrderRequestDto request) {
        return NetworkUtils.wrap(orderService.placeOrder(request));
    }
}