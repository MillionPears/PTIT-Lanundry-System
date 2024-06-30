package com.laundry.service_service.repository;

import com.laundry.service_service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    boolean existsByServiceName(String serviceName);

    @Query("SELECT s FROM Service s WHERE s.status = :status")
    List<Service> findAllByStatus(@Param("status") int status);
}
