package com.laundry.warehouse_service.controller;

import com.laundry.warehouse_service.dto.ApiResponse;
import com.laundry.warehouse_service.dto.PurchaseRequest;
import com.laundry.warehouse_service.dto.PurchaseResponse;
import com.laundry.warehouse_service.exception.GlobalCode;
import com.laundry.warehouse_service.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/purchase/")
public class PurchaseControler {

    private final PurchaseService purchaseService;
//    @PostMapping("create")
////    public ResponseEntity<ApiResponse<PurchaseResponse>> createPurchase(@RequestBody PurchaseRequest purchaseRequest){
////        PurchaseResponse purchaseResponse = purchaseService.createPurchase(purchaseRequest);
////        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Tạo thông tin Phiếu nhập thành công", purchaseResponse);
////        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
////    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<List<PurchaseResponse>>>getAll(){
        List<PurchaseResponse> list = purchaseService.getAll();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Lay danh sach phieu nhap thanh cong", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
