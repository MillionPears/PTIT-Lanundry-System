package com.laundry.order_service.service;

import com.laundry.order_service.dto.OrderDetailRequest;
import com.laundry.order_service.dto.OrderDetailResponse;
import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> createOrderDetail(List<OrderDetailRequest> requestList);
    List<OrderDetailResponse> getOrderDetail(Long orderId);
}
