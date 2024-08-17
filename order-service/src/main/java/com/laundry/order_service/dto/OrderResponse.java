package com.laundry.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    Long orderId;
    LocalDateTime orderDate;
    String note;
    LocalDate deadline;
    Long customerId;
    int status;
    Long deliveryTypeId;
    int deliveryStatus;
    String phoneNumber;
    String address;
    String customerName;
    String email;
}
