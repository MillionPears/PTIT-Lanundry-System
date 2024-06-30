package com.laundry.service_service.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntityNotFoundException extends GlobalException {
    public EntityNotFoundException(String message, String code) {
        super(code, message);
    }
}
