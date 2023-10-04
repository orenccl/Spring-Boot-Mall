package com.jun.springbootmall.dao.impl;

import com.jun.springbootmall.dao.ProductDao;
import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;
import com.jun.springbootmall.rowmapper.ProductRowMapper;
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
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, image_url," +
                " price, stock, description, created_date, last_modified_date " +
                "FROM product where  product_id = :productId;";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList.isEmpty() ? null : productList.get(0);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date) " +
                "VALUES (:product_name, :category, :image_url, :price, :stock, " +
                ":description, :created_date, :last_modified_date);";

        Map<String, Object> map = new HashMap<>();
        map.put("product_name", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("image_url", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }
}
