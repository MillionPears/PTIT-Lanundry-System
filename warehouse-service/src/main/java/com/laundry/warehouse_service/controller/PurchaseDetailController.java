package com.laundry.warehouse_service.controller;

import com.laundry.warehouse_service.dto.ApiResponse;
import com.laundry.warehouse_service.dto.PurchaseDetailRequest;
import com.laundry.warehouse_service.dto.PurchaseDetailResponse;
import com.laundry.warehouse_service.dto.PurchaseResponse;
import com.laundry.warehouse_service.exception.GlobalCode;
import com.laundry.warehouse_service.service.PurchaseDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/purchasedetail/")
public class PurchaseDetailController {
    private final PurchaseDetailService purchaseDetailService;

    @PostMapping("create/bystaffid/{staffid}")
    public ResponseEntity<ApiResponse<List<PurchaseResponse>>> createPurchaseDetail(@RequestBody List<PurchaseDetailRequest> purchaseDetailRequests,
                                                                                    @PathVariable Long staffid){
        List<PurchaseDetailResponse> list = purchaseDetailService.createPurchaseDetail(purchaseDetailRequests,staffid);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tao chi tiet phieu nhap thanh cong",list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall/bypurchaseid/{id}")
    public ResponseEntity<ApiResponse<List<PurchaseDetailResponse>>> getPurchaseDetailByPurchaseId(@PathVariable Long id){
        List<PurchaseDetailResponse> list = purchaseDetailService.getPurchaseDetailByPurchaseid(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Chi tiết phieu nhap",list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
