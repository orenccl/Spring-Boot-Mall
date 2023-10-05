package com.jun.springbootmall.dao.impl;

import com.jun.springbootmall.dao.OrderDao;
import com.jun.springbootmall.dto.OrderQueryParams;
import com.jun.springbootmall.model.OrderDetail;
import com.jun.springbootmall.model.OrderItem;
import com.jun.springbootmall.rowmapper.OrderDetailRowMapper;
import com.jun.springbootmall.rowmapper.OrderItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrderDetail(OrderQueryParams orderQueryParams) {
        String sql = "SELECT count(*) FROM order_detail WHERE 1 = 1";

        Map<String, Object> map = new HashMap<>();
        // Filter
        sql = addFilteringSql(sql, map, orderQueryParams);

        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public List<OrderDetail> getOrderDetails(OrderQueryParams orderQueryParams) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM order_detail where 1 = 1";

        Map<String, Object> map = new HashMap<>();
        // Filter
        sql = addFilteringSql(sql, map, orderQueryParams);

        // Sort
        sql += " ORDER BY created_date DESC";

        // Pagination
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new OrderDetailRowMapper());
    }

    @Override
    public OrderDetail getOrderDetailById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM order_detail where order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderDetail> orderDetailList = namedParameterJdbcTemplate.query(sql, map, new OrderDetailRowMapper());
        return orderDetailList.isEmpty() ? null : orderDetailList.get(0);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "FROM order_item as oi " +
                "LEFT JOIN product as p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
    }

    @Override
    public Integer createOrderDetail(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO order_detail (user_id, total_amount, created_date, last_modified_date) " +
                "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams) {
        if (orderQueryParams.getUserId() != null) {
            sql += " AND user_id = :userId";
            map.put("userId", orderQueryParams.getUserId());
        }

        return sql;
    }
}
