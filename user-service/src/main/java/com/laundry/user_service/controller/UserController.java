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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/")
public class UserController {

    private final AccountService accountService;
    private final AuthService authService;
    @PostMapping("register")
    public ResponseEntity<ApiResponse<AccountResponse>> registerAccount(@RequestBody RegisterRequest registerRequest){
        AccountResponse response = accountService.registerAccount(registerRequest);
        TokenResponse tokenResponse = authService.createToken(registerRequest.getUsername(),registerRequest.getPassword());

//        System.out.println(response.getUsername()+"haha");
        response.setToken(tokenResponse.getToken());
//        Long userId = accountService.getUserIdByUserName(response.getUsername());
//        response.setUserId(userId);
//        System.out.println(response.getUsername()+"haha");
//        System.out.println(response.getUserId()+"kaka");
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Đăng kí tài khoản thành công",response);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AccountResponse>> loginAccount(@RequestBody LoginRequest loginRequest){

        TokenResponse tokenResponse = authService.createToken(loginRequest.getUsername(),loginRequest.getPassword());
        Long userId = accountService.getUserIdByUserName(loginRequest.getUsername());

        AccountResponse accountResponse = new AccountResponse(loginRequest.getUsername(),tokenResponse.getToken(),userId);
        ApiResponse<AccountResponse> response = new ApiResponse<>(GlobalCode.SUCCESS,"Đăng nhập thành công",accountResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getbyusername/{username}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUserName(@PathVariable String username){

        UserResponse userResponse = accountService.getUserByUserName(username);

        ApiResponse<UserResponse> response = new ApiResponse<>(GlobalCode.SUCCESS,"Thong tin user",userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getroleid/byusername/{username}")
    public ResponseEntity<ApiResponse<Long>> getRoleIdByUserName(@PathVariable String username){
        System.out.println("username"+username);
        Long roleid = accountService.getRoleIdByUsername(username);
        String roleString = (roleid == 1) ? "KH" :
                (roleid == 2) ? "NV" :
                        (roleid == 3) ? "ADMIN" : "Không xác định";
        System.out.println("roleId"+roleString);
        ApiResponse<Long> response = new ApiResponse<>(GlobalCode.SUCCESS,roleString,roleid);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
