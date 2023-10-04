package com.jun.springbootmall.service;

import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
