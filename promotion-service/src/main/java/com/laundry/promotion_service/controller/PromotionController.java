package com.laundry.promotion_service.controller;

import com.laundry.promotion_service.dto.ApiResponse;
import com.laundry.promotion_service.dto.PromotionRequest;
import com.laundry.promotion_service.dto.PromotionResponse;
import com.laundry.promotion_service.exception.GlobalCode;
import com.laundry.promotion_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/promotion/")
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("create")
        public ResponseEntity<ApiResponse<PromotionResponse>> createService (@RequestBody PromotionRequest promotionRequest)
        {
            PromotionResponse promotionResponse = promotionService.createPromotion(promotionRequest);
            ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Tao khuyen mai moi thanh cong", promotionResponse);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<PromotionResponse>> getPromotionById (@PathVariable Long id)
    {
        PromotionResponse promotionResponse = promotionService.getPromotionById(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Thong tin khuyen mai voi id: "+id, promotionResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping ("update/{id}")
    public ResponseEntity<ApiResponse<PromotionResponse>> updatePromotion (
            @RequestBody PromotionRequest promotionRequest,
            @PathVariable Long id)
    {
        PromotionResponse promotionResponse = promotionService.updatePromotion(promotionRequest,id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Cap nhat khuyen mai thanh cong", promotionResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping ("delete/{id}")
    public ResponseEntity<ApiResponse<PromotionResponse>> deletePromotion (@PathVariable Long id)
    {
        Long  promotionId = promotionService.deletePromotion(id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Xoa khuyen mai thanh cong", promotionId);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/{status}")
    public ResponseEntity<ApiResponse<PromotionResponse>> getAllPromotionActive (@PathVariable Integer status)
    {
        String statusString = (status == 0) ? "Không hoạt động" : "Hoạt động";
        List<PromotionResponse> list = promotionService.getALlPromotionByStatus(status);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach các khuyen mai "+statusString, list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("discountrate/{id}")
    ResponseEntity<Double> getDiscountRate(@PathVariable Long id)
    {
        return ResponseEntity.ok(promotionService.getDiscountRateById(id));
    }

    @GetMapping("getall")
    public ResponseEntity<ApiResponse<PromotionResponse>> getAllPromotion ()
    {
        List<PromotionResponse> list = promotionService.getALl();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Danh sach các khuyen mai ", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PutMapping ("update/status/{status}/byid/{id}")
    public ResponseEntity<ApiResponse<PromotionResponse>> updatePromotionByStatus (@PathVariable int status,@PathVariable Long id)
    {
        PromotionResponse  promotionResponse = promotionService.updateStatusById(status,id);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS,"Thay doi trang thai khuyen mai thanh cong ", promotionResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
