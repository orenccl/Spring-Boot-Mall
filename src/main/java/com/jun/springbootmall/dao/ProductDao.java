package com.jun.springbootmall.dao;

import com.jun.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
