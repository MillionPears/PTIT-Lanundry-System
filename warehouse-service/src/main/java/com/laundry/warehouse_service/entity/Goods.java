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
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "goods_name", nullable = false, length = 100)
    private String goodsName;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "is_active", nullable = false)
    private boolean active;
    @Column(name = "threshold", nullable = false)
    private int threshold;
}
