package com.laundry.order_service.controller;

import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("update/{id}/orderstatus/{status}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable int status)
    {
        OrderResponse orderResponse = orderService.updateOrderStatus(id,status);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Cập nhật trạng thái đơn hàng thành công", orderResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}/deliverystatus/{status}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateDeliveryStatus(
            @PathVariable Long id,
            @PathVariable int status)
    {
        OrderResponse orderResponse = orderService.updateDeliveryStatus(id,status);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Cập nhật trạng thái giao hàng thành công", orderResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable Long id){
        String result = orderService.deleteOrder(id);
            ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Xóa đơn hàng thành công", result);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

    }

    @PutMapping("update/{id}/deleverytype/{type}")
    public ResponseEntity<ApiResponse<String>> updateDeliveryType(
            @PathVariable Long id,
            @PathVariable Long type)
    {
        String result = orderService.updateDeliveryType(id,type);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Cap nhat loai hinh nhan hang thanh cong", result);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("getall/bycustomerid/{customerid}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllByCustomerId (@PathVariable Long customerid){
        List<OrderResponse> list = orderService.getOrdersByCustomerId(customerid);
        ApiResponse apiResponse=new ApiResponse<>(GlobalCode.SUCCESS,"Danh sach don hang cua Khach hang la: ", list);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("getall/bystatus/{status}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllByStatus (@PathVariable int status){
        List<OrderResponse> list = orderService.getOrderByStatus(status);
        ApiResponse apiResponse=new ApiResponse<>(GlobalCode.SUCCESS,"Danh sach don hang: ", list);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAll (){
        List<OrderResponse> list = orderService.getAllOrder();
        ApiResponse apiResponse=new ApiResponse<>(GlobalCode.SUCCESS,"Danh sach don hang: ", list);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("list/shipment")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrderShipment (){
        List<OrderResponse> list = orderService.getOrderShipment();
        ApiResponse apiResponse=new ApiResponse<>(GlobalCode.SUCCESS,"Danh sach don hang: ", list);
        return ResponseEntity.ok().body(apiResponse);
    }
}
