package com.jun.springbootmall.dao;

import com.jun.springbootmall.constant.ProductCategory;
import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
