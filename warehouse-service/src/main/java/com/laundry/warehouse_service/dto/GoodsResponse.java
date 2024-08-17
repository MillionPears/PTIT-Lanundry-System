package com.laundry.warehouse_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResponse {
    Long goodsId;
    String goodsName;
    String image;
    int amount;
    boolean active;
    int threshold;
}
