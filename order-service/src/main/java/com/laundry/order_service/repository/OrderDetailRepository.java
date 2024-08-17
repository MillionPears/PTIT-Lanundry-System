package com.laundry.order_service.repository;

import com.laundry.order_service.entity.Order;
import com.laundry.order_service.entity.OrderDetail;
import com.laundry.order_service.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrder(Order order);
}
