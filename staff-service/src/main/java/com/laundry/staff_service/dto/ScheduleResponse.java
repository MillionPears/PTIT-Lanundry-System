package com.laundry.staff_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    Long scheduleId;
    LocalTime startTime;
    LocalTime endTime;
    boolean active;
}
