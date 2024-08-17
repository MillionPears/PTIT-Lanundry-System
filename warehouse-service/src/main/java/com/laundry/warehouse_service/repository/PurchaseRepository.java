package com.laundry.warehouse_service.repository;

import com.laundry.warehouse_service.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
