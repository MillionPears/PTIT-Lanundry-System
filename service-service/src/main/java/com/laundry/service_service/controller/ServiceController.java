package com.laundry.service_service.controller;

import com.laundry.service_service.dto.ApiResponse;
import com.laundry.service_service.dto.ServiceRequest;
import com.laundry.service_service.dto.ServiceResponse;
import com.laundry.service_service.dto.ServiceIdRequest;
import com.laundry.service_service.exception.GlobalCode;
import com.laundry.service_service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/service/")
public class ServiceController {

    private final ServiceService service;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<ServiceResponse>> createService (@RequestBody ServiceRequest serviceRequest)
    {
        ServiceResponse serviceResponse = service.createService(serviceRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tao dich vu moi thanh cong", serviceResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PutMapping("update/{serviceId}")
    public ResponseEntity<ApiResponse<ServiceResponse>> updateService(
            @RequestBody ServiceRequest serviceRequest,
            @PathVariable Long serviceId) {
        ServiceResponse serviceResponse = service.updateService(serviceRequest, serviceId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Cập nhật dich vu thành công", serviceResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getServiceById(@PathVariable Long id){
        ServiceResponse serviceResponse = service.getServiceById(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Thong tin dich vu theo id: "+id, serviceResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/promotionid/{promotionId}")
    public ResponseEntity<Void> updatePromotionIdToNull(@PathVariable  Long promotionId)
    {
        service.updatePromotionIdToNull(promotionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("getall/bystatus/{status}")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> ggetAllByStatus(@PathVariable int status){
        List<ServiceResponse> list = service.getServiceByStatus(status);
        String statusString = status==1 ?"Đang hoạt động" : "Không hoạt động";
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach dich vụ "+ statusString, list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/promotionid/{promotionid}/forservices")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> updatePromotionIdForServices( @PathVariable Long promotionid,@RequestBody ServiceIdRequest request){
        List<Long> serviceIds = request.getServiceIds();
        List<ServiceResponse> list = service.updatePromotionIdForServices(serviceIds, promotionid);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach dich vụ sau khi ap dung khuyen mai ", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall/bypromotionid/{promotionid}")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> ggetAllByStatus(@PathVariable Long  promotionid){
        List<ServiceResponse> list = service.getServiceByPromotionId(promotionid);

        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach dich vụ ap dung khuyen mai", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> ggetAll(){
        List<ServiceResponse> list = service.getAllService();

        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach dich vụ ", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
