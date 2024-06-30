package com.laundry.order_service.controller;

import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order/")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest orderRequest)
    {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tạo đơn hàng thành công", orderResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
