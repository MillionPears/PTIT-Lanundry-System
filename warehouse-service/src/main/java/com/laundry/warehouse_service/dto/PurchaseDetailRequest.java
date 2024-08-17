package com.laundry.warehouse_service.dto;

import com.laundry.warehouse_service.entity.PurchaseDetailId;
import lombok.Data;

@Data
public class PurchaseDetailRequest {
    PurchaseDetailId id;
    int amount;
    double priceIncome;
}
