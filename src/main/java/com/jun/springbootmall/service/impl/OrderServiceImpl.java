package com.jun.springbootmall.service.impl;

import com.jun.springbootmall.dao.OrderDao;
import com.jun.springbootmall.dao.ProductDao;
import com.jun.springbootmall.dao.UserDao;
import com.jun.springbootmall.dto.BuyItem;
import com.jun.springbootmall.dto.CreateOrderRequest;
import com.jun.springbootmall.dto.OrderQueryParams;
import com.jun.springbootmall.model.OrderDetail;
import com.jun.springbootmall.model.OrderItem;
import com.jun.springbootmall.model.Product;
import com.jun.springbootmall.model.User;
import com.jun.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrderDetail(orderQueryParams);
    }

    @Override
    public List<OrderDetail> getOrders(OrderQueryParams orderQueryParams) {
        List<OrderDetail> orderDetailList = orderDao.getOrderDetails(orderQueryParams);

        for (OrderDetail orderDetail : orderDetailList){
            orderDetail.setOrderItemList(orderDao.getOrderItemsByOrderId(orderDetail.getOrderId()));
        }

        return orderDetailList;
    }

    @Override
    public OrderDetail getOrderById(Integer orderId) {
        OrderDetail orderDetail = orderDao.getOrderDetailById(orderId);

        orderDetail.setOrderItemList(orderDao.getOrderItemsByOrderId(orderId));

        return orderDetail;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // Check if user exists
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("The userId {} not exists.", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // Check if product exists and have enough stock
            if (product == null) {
                log.warn("Product {} not exists.", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("Product {} doesn't have enough stock, could not purchase." +
                        "Remain stock {}, Buy quantity {}.", buyItem.getProductId(),
                        product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // Update product stock
            productDao.updateStock(buyItem.getProductId(), product.getStock() - buyItem.getQuantity());

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
