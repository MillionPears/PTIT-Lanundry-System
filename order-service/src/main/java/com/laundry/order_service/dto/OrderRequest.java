package com.laundry.order_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderRequest {
    LocalDateTime orderDate;
    String note;
    LocalDate deadline;
    Long customerId;
    int status;
    Long deliveryTypeId;
    int deliveryStatus;
    String phoneNumber;
    String address;
    String email;
}
