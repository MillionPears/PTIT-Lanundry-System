package com.laundry.customer_service.repository;

import com.laundry.customer_service.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    boolean existsByRankingName(String name);
}
