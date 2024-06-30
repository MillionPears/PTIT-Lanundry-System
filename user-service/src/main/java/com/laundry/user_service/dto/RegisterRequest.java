package com.laundry.user_service.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Long role;
}