package com.laundry.staff_service.controller;


import com.laundry.staff_service.dto.ApiResponse;
import com.laundry.staff_service.dto.ScheduleRequest;
import com.laundry.staff_service.dto.ScheduleResponse;
import com.laundry.staff_service.exception.GlobalCode;
import com.laundry.staff_service.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/schedule/")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(@RequestBody ScheduleRequest scheduleRequest){
        ScheduleResponse scheduleResponse = scheduleService.createSchedule(scheduleRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Them lich lam viec thanh cong",scheduleResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(
            @RequestBody ScheduleRequest scheduleRequest,
            @PathVariable Long id) {
        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(scheduleRequest, id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Cập nhật lich lam viec thành công", scheduleResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/all")
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>>getAllSchedule(){
        List<ScheduleResponse> list = scheduleService.getALlSchedule();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Danh sach lich lam viec", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> deleteSchedule(
            @PathVariable Long id) {
        Long  result = scheduleService.deleteSchedule(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Xoa lich lam viec thành công", result);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
