package com.laundry.warehouse_service.repository;

import com.laundry.warehouse_service.entity.PurchaseDetail;
import com.laundry.warehouse_service.entity.PurchaseDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, PurchaseDetailId> {
}
