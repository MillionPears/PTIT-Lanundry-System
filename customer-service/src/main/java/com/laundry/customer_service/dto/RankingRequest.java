package com.laundry.customer_service.dto;

import lombok.Data;

@Data
public class RankingRequest {
    String rankName;
    Double range;
}
