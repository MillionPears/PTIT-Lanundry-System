package com.laundry.customer_service.service;

import com.laundry.customer_service.dto.RankingRequest;
import com.laundry.customer_service.dto.RankingResponse;

public interface RankingService {
    RankingResponse createRanking(RankingRequest rankingRequest);
    RankingResponse updateRanking(RankingRequest rankingRequest,Long id);
}
