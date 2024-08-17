package com.laundry.promotion_service.repository.HttpClient;

import com.laundry.promotion_service.dto.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "service-service",url = "http://localhost:9006/api/service/")
public interface ServiceClient {
    @PutMapping("update/promotionid/{promotionId}")
    ResponseEntity<List<ServiceResponse>> updatePromotionIdToNull(@PathVariable Long promotionId);
}
