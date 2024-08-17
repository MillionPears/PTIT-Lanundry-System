package com.laundry.order_service.dto;

import com.laundry.order_service.entity.OrderDetailId;
import lombok.Data;

@Data
public class OrderDetailRequest {
    OrderDetailId id;

    double amount;
    double price;
}
