package com.laundry.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    Long serviceId;
    String serviceName;
    String description;
    Double price;
    Long staffId;
    Long promotionId;
    int status;
}
