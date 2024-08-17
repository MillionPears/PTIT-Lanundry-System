package com.laundry.promotion_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionRequest {
    String promotionName;
    Double discountRate;
    LocalDate startDate;
    LocalDate endDate;
    Integer status;
    private Long staffId;
}
