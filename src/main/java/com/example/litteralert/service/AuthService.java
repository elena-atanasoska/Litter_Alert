package com.example.litteralert.service;

import com.example.litteralert.model.User;

public interface AuthService {
    User login(String username, String password);
}
