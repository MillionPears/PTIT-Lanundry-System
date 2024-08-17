package com.laundry.customer_service.service.implement;

import com.laundry.customer_service.dto.RankingRequest;
import com.laundry.customer_service.dto.RankingResponse;
import com.laundry.customer_service.entity.Ranking;
import com.laundry.customer_service.exception.EntityExitsException;
import com.laundry.customer_service.exception.GlobalCode;
import com.laundry.customer_service.repository.RankingRepository;
import com.laundry.customer_service.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
    private final RankingRepository rankingRepository;
    private final ModelMapper modelMapper;

    @Override
    public RankingResponse createRanking(RankingRequest rankingRequest) {
        boolean checkName = rankingRepository.existsByRankingName(rankingRequest.getRankName());
        if(checkName) throw new EntityExitsException("Ten Xep hang da duoc dat truoc do", GlobalCode.ERROR_ID_EXIST);
        Ranking ranking = modelMapper.map(rankingRequest,Ranking.class);
        ranking= rankingRepository.save(ranking);
        RankingResponse response = modelMapper.map(ranking,RankingResponse.class);
        return response;
    }

    @Override
    public RankingResponse updateRanking(RankingRequest rankingRequest,Long id) {
        Ranking ranking = rankingRepository.findById(id).orElseThrow();
        if (!rankingRequest.getRankName().equals(ranking.getRankingName())) {
            boolean checkName = rankingRepository.existsByRankingName(rankingRequest.getRankName());
            if (checkName) {
                throw new EntityExitsException("Tên khuyến mãi đã tồn tại", GlobalCode.ERROR_ID_EXIST);
            }
        }
        modelMapper.map(rankingRequest,ranking);
        ranking = rankingRepository.save(ranking);
        RankingResponse rankingResponse = modelMapper.map(ranking,RankingResponse.class);
        return rankingResponse;
    }
}
