package com.laundry.warehouse_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseRequest {
    LocalDate dateCreate;
    Long staffId;
}
