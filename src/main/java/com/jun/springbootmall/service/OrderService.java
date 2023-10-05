package com.jun.springbootmall.service;

import com.jun.springbootmall.dto.CreateOrderRequest;
import com.jun.springbootmall.model.OrderDetail;

public interface OrderService {
    OrderDetail getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
