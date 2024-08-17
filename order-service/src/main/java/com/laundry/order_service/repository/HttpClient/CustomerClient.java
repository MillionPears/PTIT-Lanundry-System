package com.laundry.order_service.repository.HttpClient;

import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.CustomerResponse;
import com.laundry.order_service.dto.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="customer-service",url ="http://localhost:9001/api/customer" )
public interface CustomerClient {
    @GetMapping(value = "/getbyid/{id}")
    ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Long id);
}
