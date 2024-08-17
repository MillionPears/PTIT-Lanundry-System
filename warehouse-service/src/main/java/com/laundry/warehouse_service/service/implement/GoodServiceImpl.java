package com.laundry.warehouse_service.service.implement;

import com.laundry.warehouse_service.dto.GoodsRequest;
import com.laundry.warehouse_service.dto.GoodsResponse;
import com.laundry.warehouse_service.entity.Goods;
import com.laundry.warehouse_service.exception.EntityExitsException;
import com.laundry.warehouse_service.exception.EntityNotFoundException;
import com.laundry.warehouse_service.exception.GlobalCode;
import com.laundry.warehouse_service.repository.GoodsRepository;
import com.laundry.warehouse_service.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {
    private final ModelMapper modelMapper;
    private final GoodsRepository goodsRepository;
    @Override
    public GoodsResponse createGooods(GoodsRequest goodsRequest) {
        boolean checkName = goodsRepository.existsByGoodsName(goodsRequest.getGoodsName());
        if(checkName){
            throw new EntityExitsException("Tên vật liệu đã tồn tại", GlobalCode.ERROR_ID_EXIST);
        }
        Goods goods = modelMapper.map(goodsRequest, Goods.class);
        goods = goodsRepository.save(goods);
        GoodsResponse goodsResponse = modelMapper.map(goods,GoodsResponse.class);
        return goodsResponse;
    }

    @Override
    public GoodsResponse updateGoods(GoodsRequest goodsRequest, Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(
                ()-> new EntityNotFoundException("Khong tim thay thong tin vat lieu",GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        modelMapper.map(goodsRequest,goods);
        goods = goodsRepository.save(goods);
        GoodsResponse goodsResponse = modelMapper.map(goods,GoodsResponse.class);

        return goodsResponse;
    }

    @Override
    public List<GoodsResponse> getALl() {

        List<GoodsResponse> list = goodsRepository.findAll()
                .stream()
                .map(goods -> {
                    GoodsResponse goodsResponse = modelMapper.map(goods,GoodsResponse.class);
                    return goodsResponse;
                }).collect(Collectors.toList());
       return list;
    }

    @Override
    public GoodsResponse decreaseQuantity(Long goodsId) {
        // Tìm hàng hóa theo ID
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new EntityNotFoundException("Goods not found with id " + goodsId,GlobalCode.ERROR_ENTITY_NOT_FOUND));

        // Kiểm tra số lượng hiện tại
        if (goods.getAmount() <= 0) {
            throw new IllegalStateException("Cannot decrease quantity below zero.");
        }

        // Giảm số lượng đi 1
        goods.setAmount(goods.getAmount() - 1);

        // Lưu thay đổi vào cơ sở dữ liệu
        goods = goodsRepository.save(goods);
        GoodsResponse goodsResponse = modelMapper.map(goods,GoodsResponse.class);
        return goodsResponse;
    }

    @Override
    public GoodsResponse getGoodsById(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new EntityNotFoundException("Goods not found with id " + goodsId,GlobalCode.ERROR_ENTITY_NOT_FOUND));

        GoodsResponse goodsResponse = modelMapper.map(goods,GoodsResponse.class);
        return goodsResponse;
    }
}
