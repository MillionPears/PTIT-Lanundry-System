package com.laundry.warehouse_service.controller;

import com.laundry.warehouse_service.dto.ApiResponse;
import com.laundry.warehouse_service.dto.GoodsRequest;
import com.laundry.warehouse_service.dto.GoodsResponse;
import com.laundry.warehouse_service.exception.GlobalCode;
import com.laundry.warehouse_service.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/goods/")
public class GoodsController {
    private final GoodService goodService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<GoodsResponse>> createCustomer(@RequestBody GoodsRequest goodsRequest){
        GoodsResponse goodsResponse = goodService.createGooods(goodsRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Tạo thông tin vật liệu thành công", goodsResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<GoodsResponse>> updateCustomer(@RequestBody GoodsRequest goodsRequest,@PathVariable Long id){
        GoodsResponse goodsResponse = goodService.updateGoods(goodsRequest,id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Cập nhật thông tin vật liệu thành công", goodsResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<List<GoodsResponse>>> getAll(){
        List<GoodsResponse> list = goodService.getALl();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Danh sách vật liệu", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PutMapping("decrease/{id}")
    public  ResponseEntity<ApiResponse<GoodsResponse>> decreaseGoodsQuantity(@PathVariable Long id) {
        GoodsResponse goodsResponse = goodService.decreaseQuantity(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Đã cập nhật số lượng trong kho", goodsResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("get/byid/{id}")
    public ResponseEntity<ApiResponse<GoodsResponse>> getById(@PathVariable Long id){
        GoodsResponse goodsResponse = goodService.getGoodsById(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Thông tin vật liệu",goodsResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
