package com.jun.springbootmall.dao;

import com.jun.springbootmall.dto.UserRegisterRequest;
import com.jun.springbootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
