package com.laundry.warehouse_service.dto;

import lombok.Data;

@Data
public class GoodsRequest {
    String goodsName;
    String image;
    int amount;
    boolean active;
    int threshold;
}
