package com.jun.springbootmall.dao;

import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
