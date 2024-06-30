package com.laundry.order_service.repository;

import com.laundry.order_service.entity.OrderDetail;
import com.laundry.order_service.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
