package com.laundry.user_service.service;

import com.laundry.user_service.dto.AccountResponse;
import com.laundry.user_service.dto.RegisterRequest;

import com.laundry.user_service.dto.UserResponse;
import com.laundry.user_service.entity.Account;


public interface AccountService {
    AccountResponse registerAccount(RegisterRequest registerRequest);
    Account getAccount(String username);
    Long getRoleIdByUsername(String username);
    UserResponse getUserByUserName(String username);
    Long getUserIdByUserName(String username);
}
