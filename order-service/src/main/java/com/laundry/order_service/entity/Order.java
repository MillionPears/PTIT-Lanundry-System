package com.laundry.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "`order`")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "status", length = 50, nullable = false)
    private int status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery_Type deliveryType;
}
