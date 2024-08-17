package com.laundry.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class UserResponse<T> {
    private T data;
}