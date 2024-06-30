package com.laundry.order_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    LocalDate orderDate;
    String note;
    LocalDate deadline;
    Long customerId;
    int status;
    Long delivery_TypeId;
}
