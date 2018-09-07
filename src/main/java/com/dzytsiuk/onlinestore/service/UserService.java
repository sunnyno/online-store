package com.dzytsiuk.onlinestore.service;

import com.dzytsiuk.onlinestore.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String login);
}