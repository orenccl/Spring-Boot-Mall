package com.jun.springbootmall.dao;

import com.jun.springbootmall.dto.CreateOrderRequest;
import com.jun.springbootmall.model.OrderDetail;
import com.jun.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    OrderDetail getOrderDetailById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrderDetail(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
