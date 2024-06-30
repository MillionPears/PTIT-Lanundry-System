package com.laundry.service_service.controller;

import com.laundry.service_service.dto.ApiResponse;
import com.laundry.service_service.dto.ServiceRequest;
import com.laundry.service_service.dto.ServiceResponse;
import com.laundry.service_service.exception.GlobalCode;
import com.laundry.service_service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<ServiceResponse>> updateCustomer(
            @RequestBody ServiceRequest serviceRequest,
            @PathVariable Long serviceId) {
        ServiceResponse serviceResponse = service.updateService(serviceRequest, serviceId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Cập nhật dich vu thành công", serviceResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
