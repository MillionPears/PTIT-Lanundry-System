package com.laundry.staff_service.entity;

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
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_name", nullable = false, length = 100)
    private String staffName;

    @Column(name = "position", nullable = false, length = 100)
    private String position;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "phone_number",unique = true, length = 20)
    private String phoneNumber;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;
}
