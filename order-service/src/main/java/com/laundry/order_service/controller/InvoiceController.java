package com.laundry.order_service.controller;

import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.InvoiceResponse;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/invoice/")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PutMapping("update/status/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> updatePaymentStatus(@PathVariable Long id){
        InvoiceResponse invoiceResponse = invoiceService.updatePaymentStatus(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Hóa đơn đã thanh toán thành công", invoiceResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAll(){
        List<InvoiceResponse> list = invoiceService.getALl();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach hoa don", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
