package com.jun.springbootmall.service;

import com.jun.springbootmall.dto.ProductQueryParams;
import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);
}
