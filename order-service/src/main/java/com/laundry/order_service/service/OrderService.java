package com.laundry.order_service.service;

import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateDeliveryStatus(Long id, int status);
    String deleteOrder(Long id);
    String updateDeliveryType(Long id, Long type);
    OrderResponse updateOrderStatus(Long id, int status);
    List<OrderResponse> getOrdersByCustomerId(Long customerId);
    List<OrderResponse> getOrderByStatus( int status);
    List<OrderResponse> getAllOrder( );
    List<OrderResponse> getOrderShipment();
}
