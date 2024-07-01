package com.laundry.staff_service.controller;


import com.laundry.staff_service.dto.ApiResponse;
import com.laundry.staff_service.dto.StaffRequest;
import com.laundry.staff_service.dto.StaffResponse;
import com.laundry.staff_service.exception.GlobalCode;
import com.laundry.staff_service.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/staff/")
public class StaffController {
    private final StaffService staffService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(@RequestBody StaffRequest staffRequest){
        StaffResponse staffResponse = staffService.createStaff(staffRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Them nhan vien thanh cong", staffResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffById(@PathVariable Long id){
        StaffResponse staffResponse = staffService.getStaffById(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Thong tin nhan vien theo id: "+id, staffResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaff(@RequestBody StaffRequest staffRequest,@PathVariable Long id){
        StaffResponse staffResponse = staffService.updateStaff(staffRequest,id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Cap nhat thong tin nhan vien thanh cong", staffResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


    @GetMapping("list/all")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getAllStaff(){
        List<StaffResponse> list = staffService.getAllStaff();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Danh sach nhan vien", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }


}
