package com.laundry.staff_service.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ScheduleDetailId implements Serializable {

    private Long scheduleId;

    private Long staffId;

    // Các phương thức getter và setter (nếu cần)
}
