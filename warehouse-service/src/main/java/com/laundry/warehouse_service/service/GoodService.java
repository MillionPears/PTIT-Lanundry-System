package com.laundry.warehouse_service.service;

import com.laundry.warehouse_service.dto.GoodsRequest;
import com.laundry.warehouse_service.dto.GoodsResponse;

import java.util.List;

public interface GoodService {
    GoodsResponse createGooods(GoodsRequest goodsRequest);
    GoodsResponse updateGoods(GoodsRequest goodsRequest, Long goodsId);
    List<GoodsResponse> getALl();
    GoodsResponse decreaseQuantity(Long goodsId);
    GoodsResponse getGoodsById(Long goodsId);
}
