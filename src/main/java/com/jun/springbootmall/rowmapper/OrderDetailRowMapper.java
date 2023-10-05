package com.jun.springbootmall.rowmapper;

import com.jun.springbootmall.model.OrderDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailRowMapper implements RowMapper<OrderDetail> {
    @Override
    public OrderDetail mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderId(resultSet.getInt("order_id"));
        orderDetail.setUserId(resultSet.getInt("user_id"));
        orderDetail.setTotalAmount(resultSet.getInt("total_amount"));
        orderDetail.setCreatedDate(resultSet.getTimestamp("created_date"));
        orderDetail.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));

        return orderDetail;
    }
}
