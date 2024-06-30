package com.laundry.user_service.controller;


import com.laundry.user_service.dto.AccountResponse;
import com.laundry.user_service.dto.*;
import com.laundry.user_service.dto.LoginRequest;
import com.laundry.user_service.dto.RegisterRequest;
import com.laundry.user_service.exception.GlobalCode;
import com.laundry.user_service.service.AccountService;

import com.laundry.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/")
public class UserController {

    private final AccountService accountService;
    private final AuthService authService;
    @PostMapping("register")
    public ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest){
        AccountResponse response = accountService.registerAccount(registerRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Đăng kí tài khoản thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<TokenResponse>> loginAccount(@RequestBody LoginRequest loginRequest){

        TokenResponse tokenResponse = authService.createToken(loginRequest);
        ApiResponse<TokenResponse> response = new ApiResponse<>(GlobalCode.SUCCESS,"Đăng nhập thành công",tokenResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
