package com.jun.springbootmall.service;

import com.jun.springbootmall.dto.UserRegisterRequest;
import com.jun.springbootmall.model.User;

public interface UserService {
    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
