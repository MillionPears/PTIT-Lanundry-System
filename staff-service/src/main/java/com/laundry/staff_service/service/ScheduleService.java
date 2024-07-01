package com.laundry.staff_service.service;

import com.laundry.staff_service.dto.ScheduleRequest;
import com.laundry.staff_service.dto.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse createSchedule(ScheduleRequest scheduleRequest);
    ScheduleResponse updateSchedule(ScheduleRequest scheduleRequest, Long id);
    Long deleteSchedule(Long id);
    List<ScheduleResponse> getALlSchedule();
}
