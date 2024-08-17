package com.laundry.warehouse_service.service;

import com.laundry.warehouse_service.dto.PurchaseRequest;
import com.laundry.warehouse_service.dto.PurchaseResponse;
import com.laundry.warehouse_service.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    Purchase createPurchase(Long staffId);
    List<PurchaseResponse> getAll();
}
