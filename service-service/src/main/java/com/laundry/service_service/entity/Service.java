package com.laundry.service_service.entity;

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
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name",unique = true, nullable = false, length = 100)
    private String serviceName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "staff_id", nullable = false)
    private Long staffId; // Reference to Staff in another service

    @Column(name = "promotion_id")
    private Long promotionId; // Reference to Promotion in another service
    @Column(name = "status", nullable = false)
    private int status; // Reference to Promotion in another service

}
