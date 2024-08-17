package com.laundry.service_service.repository;

import com.laundry.service_service.dto.ServiceResponse;
import com.laundry.service_service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    boolean existsByServiceName(String serviceName);

    @Query("SELECT s FROM Service s WHERE s.status = :status")
    List<Service> findAllByStatus(@Param("status") int status);

    @Modifying
    @Transactional
    @Query("UPDATE Service s SET s.promotionId = NULL WHERE s.promotionId = :promotionId")
    void updatePromotionIdToNull(@Param("promotionId") Long promotionId);

    @Query("SELECT s FROM Service s WHERE s.promotionId = :promotionId")
    List<Service> findAllByPromotionId(@Param("promotionId") Long statpromotionIdus);
}
