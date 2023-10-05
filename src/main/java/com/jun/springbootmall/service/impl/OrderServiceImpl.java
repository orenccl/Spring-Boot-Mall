package com.jun.springbootmall.service.impl;

import com.jun.springbootmall.dao.OrderDao;
import com.jun.springbootmall.dao.ProductDao;
import com.jun.springbootmall.dto.BuyItem;
import com.jun.springbootmall.dto.CreateOrderRequest;
import com.jun.springbootmall.model.OrderItem;
import com.jun.springbootmall.model.Product;
import com.jun.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // Calculate total amount
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            // Convert BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // Create order detail
        Integer orderId = orderDao.createOrderDetail(userId, totalAmount);

        // Create order item
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
