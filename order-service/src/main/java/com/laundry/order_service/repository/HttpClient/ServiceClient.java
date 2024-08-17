package com.laundry.order_service.repository.HttpClient;

import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="service-service",url ="http://localhost:9006/api/service" )
public interface ServiceClient {
    @GetMapping(value = "/byId/{id}")
    ResponseEntity<ApiResponse<ServiceResponse>> getServiceById(@PathVariable Long id);
}
