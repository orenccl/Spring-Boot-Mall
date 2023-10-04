package com.jun.springbootmall.dao;

import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
