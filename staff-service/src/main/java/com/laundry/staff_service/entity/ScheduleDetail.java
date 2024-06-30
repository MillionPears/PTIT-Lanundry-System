package com.laundry.staff_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ScheduleDetail {
    @EmbeddedId
    private ScheduleDetailId id;

    @ManyToOne
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @MapsId("staffId")
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
