package com.laundry.service_service.dto;

import lombok.Data;

@Data
public class ServiceRequest {
    String serviceName;
    String description;
    Double price;
    Long staffId;
    Long promotionId;
    int status;
}
