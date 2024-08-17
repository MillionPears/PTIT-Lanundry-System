package com.laundry.promotion_service.repository;

import com.laundry.promotion_service.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion,Long> {
    boolean existsByPromotionName(String name);

    List<Promotion> findByStatus(Integer status);

}
