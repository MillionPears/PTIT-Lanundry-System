package com.laundry.staff_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ScheduleRequest {
    LocalTime startTime;
    LocalTime endTime;
    boolean active;
}
