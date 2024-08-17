package com.laundry.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime orderDate;

    @Column(name = "note", length = 255)
    private String note;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "phonenumber", nullable = false)
    private String phoneNumber;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "notified")
    private boolean notified;
    @Column(name = "status", length = 50, nullable = false)
    private int status; // 0: vừa tạo lịch hẹn
                        // 1: da nhan do giat
                        // 2: dang trong qua trinh giat
                        // 3: da hoan thanh giat
                        // 4: da huy don hang( chi duoc huy khi status = 0)

    @Column(name = "deliveryStatus")
    private int deliveryStatus; // 0: không giao
                                // 1: đang chuẩn bị
                                // 2: đang giao
                                // 3: đã hoàn thaành



    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_id", nullable = true,unique = false)
    private Delivery_Type deliveryType; //1: nhận trực tiep
                                        // 2: giao tân nơi
}
