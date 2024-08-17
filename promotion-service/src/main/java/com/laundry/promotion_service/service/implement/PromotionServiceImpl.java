package com.laundry.promotion_service.service.implement;

import com.laundry.promotion_service.dto.PromotionRequest;
import com.laundry.promotion_service.dto.PromotionResponse;
import com.laundry.promotion_service.dto.ServiceResponse;
import com.laundry.promotion_service.entity.Promotion;
import com.laundry.promotion_service.exception.EntityExitsException;
import com.laundry.promotion_service.exception.EntityNotFoundException;
import com.laundry.promotion_service.exception.GlobalCode;
import com.laundry.promotion_service.repository.HttpClient.ServiceClient;
import com.laundry.promotion_service.repository.PromotionRepository;
import com.laundry.promotion_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final ModelMapper modelMapper;
    private final ServiceClient serviceClient;

    @Override
    public PromotionResponse createPromotion(PromotionRequest promotionRequest) {
        boolean checkName = promotionRepository.existsByPromotionName(promotionRequest.getPromotionName());
        if(checkName) throw new EntityExitsException("Ten khuyen mai da duoc dat truoc do", GlobalCode.ERROR_ID_EXIST);
        Promotion promotion = modelMapper.map(promotionRequest,Promotion.class);
        promotion= promotionRepository.save(promotion);
        PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
        return promotionResponse;
    }

    @Override
    public PromotionResponse getPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("khong tim thay khuyen mai voi id = "+id, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
        return promotionResponse;
    }

    @Override
    public PromotionResponse updatePromotion(PromotionRequest promotionRequest, Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow();
        if (!promotionRequest.getPromotionName().equals(promotion.getPromotionName())) {
            boolean checkName = promotionRepository.existsByPromotionName(promotionRequest.getPromotionName());
            if (checkName) {
                throw new EntityExitsException("Tên khuyến mãi đã tồn tại", GlobalCode.ERROR_ID_EXIST);
            }
        }
        modelMapper.map(promotionRequest,promotion);

        promotion = promotionRepository.save(promotion);
        PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
        return promotionResponse;
    }

    @Override
    public Long deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow();
        promotion.setStatus(0);
        promotion=promotionRepository.save(promotion);
        serviceClient.updatePromotionIdToNull(promotion.getPromotionId()).getBody();
        PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
        return promotionResponse.getPromotionId();
    }

    @Override
    public List<PromotionResponse> getALlPromotionByStatus(Integer status) {
        List<Promotion> list = promotionRepository.findByStatus(status);

        return list.stream()
                .map(promotion -> {
                    PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
                    return promotionResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Double getDiscountRateById(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow();
        return promotion.getDiscountRate();
    }

    @Override
    public List<PromotionResponse> getALl() {
        List<Promotion> list = promotionRepository.findAll();
        return list.stream()
                .map(promotion -> {
                    PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
                    return promotionResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PromotionResponse updateStatusById(int status,Long id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow();
        promotion.setStatus(status);
        promotion=promotionRepository.save(promotion);
        PromotionResponse promotionResponse = modelMapper.map(promotion,PromotionResponse.class);
        return promotionResponse;
    }
}
