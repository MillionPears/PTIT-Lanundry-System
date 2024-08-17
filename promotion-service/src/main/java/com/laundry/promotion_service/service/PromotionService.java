package com.laundry.promotion_service.service;

import com.laundry.promotion_service.dto.PromotionRequest;
import com.laundry.promotion_service.dto.PromotionResponse;

import java.util.List;

public interface PromotionService {
    PromotionResponse createPromotion(PromotionRequest promotionRequest);
    PromotionResponse getPromotionById(Long id);
    PromotionResponse updatePromotion(PromotionRequest promotionRequest, Long id);
    Long deletePromotion(Long id);
    List<PromotionResponse> getALlPromotionByStatus(Integer status);
    Double getDiscountRateById(Long id);
    List<PromotionResponse> getALl();
    PromotionResponse updateStatusById(int status,Long id);
}
