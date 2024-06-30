package com.laundry.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Invoice {
    @EmbeddedId
    private OrderDetailId id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;
}
