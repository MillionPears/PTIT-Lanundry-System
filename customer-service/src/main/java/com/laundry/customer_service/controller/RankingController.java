package com.laundry.customer_service.controller;

import com.laundry.customer_service.dto.ApiResponse;
import com.laundry.customer_service.dto.RankingRequest;
import com.laundry.customer_service.dto.RankingResponse;
import com.laundry.customer_service.exception.GlobalCode;
import com.laundry.customer_service.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ranking/")
public class RankingController {
    private final RankingService rankingService;
    @PostMapping("create")
    public ResponseEntity<ApiResponse<RankingResponse>> createRanking (@RequestBody RankingRequest rankingRequest)
    {
        RankingResponse rankingResponse = rankingService.createRanking(rankingRequest);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tao Xep hang moi thanh cong", rankingResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<RankingResponse>> updateRanking (
            @RequestBody RankingRequest rankingRequest,
            @PathVariable Long id)
    {
        RankingResponse rankingResponse = rankingService.updateRanking(rankingRequest,id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Cap nhat xep hang thanh cong", rankingResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
