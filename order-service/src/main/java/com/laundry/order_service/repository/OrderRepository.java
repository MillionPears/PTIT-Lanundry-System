package com.laundry.order_service.repository;

import com.laundry.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByOrderDateBetween(LocalDateTime now, LocalDateTime twoHoursLater);
    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.notified = true WHERE o.id = :orderId")
    void updateNotifiedStatus(@Param("orderId") Long orderId);
}
