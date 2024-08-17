package com.laundry.warehouse_service.service.implement;

import com.laundry.warehouse_service.dto.PurchaseDetailRequest;
import com.laundry.warehouse_service.dto.PurchaseDetailResponse;
import com.laundry.warehouse_service.entity.Goods;
import com.laundry.warehouse_service.entity.Purchase;
import com.laundry.warehouse_service.entity.PurchaseDetail;
import com.laundry.warehouse_service.entity.PurchaseDetailId;
import com.laundry.warehouse_service.repository.GoodsRepository;
import com.laundry.warehouse_service.repository.PurchaseDetailRepository;
import com.laundry.warehouse_service.repository.PurchaseRepository;
import com.laundry.warehouse_service.service.PurchaseDetailService;
import com.laundry.warehouse_service.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    private final ModelMapper modelMapper;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final PurchaseRepository purchaseRepository;
    private final GoodsRepository goodsRepository;
    private final PurchaseService purchaseService;
    @Override
    @Transactional
    public List<PurchaseDetailResponse> createPurchaseDetail(List<PurchaseDetailRequest> requestList,Long staffId) {

         Purchase purchase = purchaseService.createPurchase(staffId);
         purchase = purchaseRepository.save(purchase);
        final Purchase finalPurchase = purchase; // Tạo biến final để sử dụng trong lambda
        Long purchaseId = purchase.getPurchaseId();

         List<PurchaseDetail> purchaseDetailList = requestList.stream()
                .map(purchaseDetailRequest -> {
                    PurchaseDetail purchaseDetail = new PurchaseDetail();
                    PurchaseDetailId purchaseDetailId = new PurchaseDetailId();
                    purchaseDetailId.setPurchaseId(purchaseId);
                    purchaseDetailId.setGoodsId(purchaseDetailRequest.getId().getGoodsId());
                    purchaseDetail.setId(purchaseDetailId);
                    // Ánh xạ các thuộc tính còn lại từ PurchaseDetailRequest
                    purchaseDetail.setAmount(purchaseDetailRequest.getAmount());
                    purchaseDetail.setPriceIncome(purchaseDetailRequest.getPriceIncome());

                    // Tạo Purchase và xử lý lỗi nếu có


                    Goods goods = goodsRepository.findById(purchaseDetailRequest.getId().getGoodsId())
                            .orElseThrow(() -> new RuntimeException("Goods not found"));
                    goods.setAmount(purchaseDetailRequest.getAmount());
                    goods = goodsRepository.save(goods);

                    purchaseDetail.setPurchase(finalPurchase);
                    purchaseDetail.setGoods(goods);
                    return purchaseDetail;
                }).collect(Collectors.toList());

        purchaseDetailRepository.saveAll(purchaseDetailList);
        return purchaseDetailList.stream()
                .map(purchaseDetail -> {
                    PurchaseDetailResponse purchaseDetailResponse = modelMapper.map(purchaseDetail, PurchaseDetailResponse.class);
                    purchaseDetailResponse.setGoodsName(purchaseDetail.getGoods().getGoodsName());
                    return purchaseDetailResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDetailResponse> getPurchaseDetailByPurchaseid(Long purchaseId) {
        List<PurchaseDetailResponse> list = purchaseDetailRepository.findAll()
                .stream()
                .filter(purchaseDetail -> purchaseDetail.getId().getPurchaseId()==purchaseId)
                .map(purchaseDetail -> {
                    PurchaseDetailResponse purchaseDetailResponse = modelMapper.map(purchaseDetail,PurchaseDetailResponse.class);
                    purchaseDetailResponse.setGoodsName(purchaseDetail.getGoods().getGoodsName());

                    return purchaseDetailResponse;
                }).collect(Collectors.toList());
        return list;
    }
}
