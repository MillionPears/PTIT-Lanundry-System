package com.laundry.service_service.repository.HttpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "promotion-service", url = "http://localhost:9005/api/promotion")
public interface PromotionClient {
    @GetMapping(value = "/discountrate/{id}")
    ResponseEntity<Double> getDiscountRate(@PathVariable Long id);
}
