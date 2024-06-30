package com.laundry.order_service.service;

import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);

}
