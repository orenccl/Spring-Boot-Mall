package com.jun.springbootmall.controller;

import com.jun.springbootmall.constant.ProductCategory;
import com.jun.springbootmall.dto.CreateOrderRequest;
import com.jun.springbootmall.dto.OrderQueryParams;
import com.jun.springbootmall.dto.ProductQueryParams;
import com.jun.springbootmall.model.OrderDetail;
import com.jun.springbootmall.model.Product;
import com.jun.springbootmall.service.OrderService;
import com.jun.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<OrderDetail>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        // Get order list
        List<OrderDetail> orderList = orderService.getOrders(orderQueryParams);
        // Get order total amount
        Integer total = orderService.countOrder(orderQueryParams);
        // Pagination
        Page<OrderDetail> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(orderList);

        return ResponseEntity.ok(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        OrderDetail order = orderService.getOrderById(orderId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(order);
    }
}
