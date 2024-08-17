package com.laundry.warehouse_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    Long purchaseId;
    LocalDate dateCreate;
    Long staffId;
}
