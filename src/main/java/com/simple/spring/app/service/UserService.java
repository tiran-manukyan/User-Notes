package com.simple.spring.app.service;

import com.simple.spring.app.entity.User;

public interface UserService {

    void createUser(User user);

    User getCurrentUser();

    User findUserByEmail(String email);
}