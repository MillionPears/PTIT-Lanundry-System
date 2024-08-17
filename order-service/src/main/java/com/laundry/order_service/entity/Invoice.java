package com.laundry.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP")
    private OffsetDateTime createdDate; // Thay đổi từ LocalDateTime sang OffsetDateTime

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_status", nullable = false)
    private int paymentStatus; // 1: da thanh toan
                                // 0: chua thanh toan

}
