package com.laundry.warehouse_service.dto;

import com.laundry.warehouse_service.entity.PurchaseDetailId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailResponse {
    PurchaseDetailId id;
    String goodsName;
    int amount;
    double priceIncome;
}
