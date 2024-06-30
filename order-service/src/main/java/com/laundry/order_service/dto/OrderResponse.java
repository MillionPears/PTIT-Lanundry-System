package com.laundry.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    Long orderId;
    LocalDate orderDate;
    String note;
    LocalDate deadline;
    Long customerId;
    int status;
    Long delivery_TypeId;
}
