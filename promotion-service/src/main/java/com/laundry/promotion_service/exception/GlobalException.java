package com.laundry.promotion_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{
    private String code;
    private String message;

    public GlobalException(String message) {
        super(message);
    }
}
