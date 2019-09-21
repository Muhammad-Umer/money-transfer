package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.User;

import java.util.List;

public interface UserService {
    User add(User user);

    User update(User user);

    boolean delete(String userId);

    User findById(String userId);

    List<User> getAll();
}
