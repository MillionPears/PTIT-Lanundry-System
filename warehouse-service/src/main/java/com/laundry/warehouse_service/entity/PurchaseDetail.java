package com.laundry.warehouse_service.entity;

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
public class PurchaseDetail {
    @EmbeddedId
    private PurchaseDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id", referencedColumnName = "purchase_id")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("goodsId")
    @JoinColumn(name = "goods_id", referencedColumnName = "goods_id")
    private Goods goods;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "price_income", nullable = false)
    private double priceIncome;
}
