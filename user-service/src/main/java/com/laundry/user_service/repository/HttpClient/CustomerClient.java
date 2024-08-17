package com.laundry.user_service.repository.HttpClient;


import com.laundry.user_service.dto.ApiResponse;
import com.laundry.user_service.dto.CustomerResponse;
import com.laundry.user_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="customer-service",url ="http://localhost:9001/api/customer" )
public interface CustomerClient {
    @GetMapping(value = "/getbyusername/{username}")
    ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByUserName(@PathVariable String username);
}
