package com.jun.springbootmall.service.impl;

import com.jun.springbootmall.dao.UserDao;
import com.jun.springbootmall.dto.UserRegisterRequest;
import com.jun.springbootmall.model.User;
import com.jun.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
