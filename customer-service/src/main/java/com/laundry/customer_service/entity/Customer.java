package com.laundry.customer_service.entity;

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
public class Customer {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "customer_id" )
    private Long id;

    @Column(name ="customer_name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_number",unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "hobbie", length = 255)
    private String hobbie;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rank_id", nullable = false)
    private Ranking ranking;
}
