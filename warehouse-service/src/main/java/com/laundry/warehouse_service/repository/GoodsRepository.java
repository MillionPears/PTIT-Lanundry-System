package com.laundry.warehouse_service.repository;

import com.laundry.warehouse_service.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods,Long> {
    boolean existsByGoodsName(String goodsName);
}
