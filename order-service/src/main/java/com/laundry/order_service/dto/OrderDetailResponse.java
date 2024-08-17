package com.laundry.order_service.dto;

import com.laundry.order_service.entity.OrderDetailId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    OrderDetailId id;
    String serviceName;
    double amount;
    double price;
}
