package com.jun.springbootmall.service;

import com.jun.springbootmall.dto.CreateOrderRequest;
import com.jun.springbootmall.dto.OrderQueryParams;
import com.jun.springbootmall.model.OrderDetail;

import java.util.List;

public interface OrderService {
    Integer countOrder(OrderQueryParams orderQueryParams);

    List<OrderDetail> getOrders(OrderQueryParams orderQueryParams);

    OrderDetail getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
