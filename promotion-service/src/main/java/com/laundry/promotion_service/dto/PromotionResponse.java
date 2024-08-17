package com.laundry.promotion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse {
    Long promotionId;
    String promotionName;
    Double discountRate;
    LocalDate startDate;
    LocalDate endDate;
    Integer status;
    private Long staffId;
}
