package com.laundry.user_service.service;


import com.laundry.user_service.dto.LoginRequest;
import com.laundry.user_service.dto.TokenResponse;

public interface AuthService {
    TokenResponse createToken(String username, String password);

}
