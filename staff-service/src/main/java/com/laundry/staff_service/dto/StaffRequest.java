package com.laundry.staff_service.dto;

import lombok.Data;

@Data
public class StaffRequest {
    String staffName;
    String position;
    String email;
    String phoneNumber;
    String avatar;
    String username;
}
