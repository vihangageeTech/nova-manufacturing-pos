package com.example.pos.service;

import com.example.pos.constant.MessageConstants;
import com.example.pos.dto.OrderItemRequestDto;
import com.example.pos.dto.OrderRequestDto;
import com.example.pos.entity.loyaltyCustomer.LoyaltyCustomer;
import com.example.pos.entity.order.Order;
import com.example.pos.entity.order.OrderStatus;
import com.example.pos.entity.orderItem.OrderItem;
import com.example.pos.entity.product.Product;
import com.example.pos.network.ResponseData;
import com.example.pos.repository.ILoyaltyCustomerRepository;
import com.example.pos.repository.IOrderRepository;
import com.example.pos.repository.IProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class OrderServiceImpl {

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final ILoyaltyCustomerRepository loyaltyCustomerRepository;

    public OrderServiceImpl(IOrderRepository orderRepository, IProductRepository productRepository, ILoyaltyCustomerRepository loyaltyCustomerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.loyaltyCustomerRepository = loyaltyCustomerRepository;
    }

    public ResponseData<Order> placeOrder(OrderRequestDto request) {
        Set<Long> uniqueProductIds = new HashSet<>();
        for (OrderItemRequestDto itemDto : request.getItems()) {
            if (!uniqueProductIds.add(itemDto.getProductId())) {
                return ResponseData.error(HttpStatus.BAD_REQUEST, MessageConstants.DUPLICATE_PRODUCTS_IN_ORDER);
            }
        }

        LoyaltyCustomer customer = loyaltyCustomerRepository.findByMobileNumber(request.getMobileNumber())
                .orElseGet(() -> {
                    LoyaltyCustomer newCustomer = new LoyaltyCustomer();
                    newCustomer.setMobileNumber(request.getMobileNumber());
                    newCustomer.setFullName("Customer " + request.getMobileNumber());
                    newCustomer.setLoyaltyPoints(0);
                    return loyaltyCustomerRepository.save(newCustomer);
                });

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.COMPLETED);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDto itemDto : request.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElse(null);

            if (product == null) {
                return ResponseData.error(HttpStatus.NOT_FOUND, MessageConstants.PRODUCT_NOT_FOUND + " with ID: " + itemDto.getProductId());
            }

            if (Boolean.FALSE.equals(product.getActive())) {
                return ResponseData.error(HttpStatus.BAD_REQUEST, "Product is inactive: " + product.getProductName());
            }

            if (product.getAvailableQuantity() < itemDto.getQuantity()) {
                return ResponseData.error(HttpStatus.BAD_REQUEST, MessageConstants.INSUFFICIENT_STOCK + " for product: " + product.getProductName());
            }

            product.setAvailableQuantity(product.getAvailableQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            BigDecimal subtotal = product.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(product.getUnitPrice());
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
        }

        int pointsEarned = totalAmount.divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN).intValue();

        order.setTotalAmount(totalAmount);
        order.setLoyaltyPointsEarned(pointsEarned);

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + pointsEarned);
        loyaltyCustomerRepository.save(customer);

        Order savedOrder = orderRepository.save(order);

        return ResponseData.success(savedOrder, MessageConstants.ORDER_PLACED_SUCCESSFULLY);
    }

}