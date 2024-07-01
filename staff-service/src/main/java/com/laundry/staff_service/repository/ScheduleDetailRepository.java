package com.laundry.staff_service.repository;

import com.laundry.staff_service.entity.ScheduleDetail;
import com.laundry.staff_service.entity.ScheduleDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, ScheduleDetailId> {
}
