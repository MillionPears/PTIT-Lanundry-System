package com.laundry.order_service.repository;

import com.laundry.order_service.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface invoiceRepository extends JpaRepository<Invoice,Long> {
}