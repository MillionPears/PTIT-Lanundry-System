package com.laundry.staff_service.dto;

import lombok.Data;

@Data
public class StaffRequest {
    String name;
    String position;
    String email;
    String phoneNumber;
    String avatar;
    String username;
    int status;
}
