package com.laundry.order_service.controller;

import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.OrderDetailRequest;
import com.laundry.order_service.dto.OrderDetailResponse;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orderdetail/")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> createOderDetail(@RequestBody List<OrderDetailRequest> orderDetailRequests){
        List<OrderDetailResponse> list = orderDetailService.createOrderDetail(orderDetailRequests);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tao chi tiet don hang thanh cong",list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getdetail/byorderid/{orderid}")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> getOrderDetailByOrderId(@PathVariable Long orderid){
        List<OrderDetailResponse> list = orderDetailService.getOrderDetail(orderid);
        System.out.println("haha"+list.toString());
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Chi tiết đơn hàng",list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
