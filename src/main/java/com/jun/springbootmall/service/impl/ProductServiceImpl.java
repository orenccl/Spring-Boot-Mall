package com.jun.springbootmall.service.impl;

import com.jun.springbootmall.dao.ProductDao;
import com.jun.springbootmall.dto.ProductRequest;
import com.jun.springbootmall.model.Product;
import com.jun.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
