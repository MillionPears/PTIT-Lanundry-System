package com.laundry.user_service.repository.HttpClient;


import com.laundry.user_service.dto.ApiResponse;
import com.laundry.user_service.dto.StaffResponse;
import com.laundry.user_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="staff-service",url ="http://localhost:9003/api/staff" )
public interface StaffClient {
    @GetMapping(value = "/getbyusername/{username}")
    ResponseEntity<ApiResponse<StaffResponse>> getStaffByUsername(@PathVariable String username);
}
