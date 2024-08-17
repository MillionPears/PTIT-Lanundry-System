package com.laundry.warehouse_service.service;

import com.laundry.warehouse_service.dto.PurchaseDetailRequest;
import com.laundry.warehouse_service.dto.PurchaseDetailResponse;
import com.laundry.warehouse_service.entity.PurchaseDetail;

import java.util.List;

public interface PurchaseDetailService {
    List<PurchaseDetailResponse> createPurchaseDetail(List<PurchaseDetailRequest> requestList,Long staffId);
    List<PurchaseDetailResponse> getPurchaseDetailByPurchaseid(Long purchaseId);

}
