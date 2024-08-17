package com.laundry.staff_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {
    Long id;
    String name;
    String position;
    String email;
    String phoneNumber;
    String avatar;
    String username;
    int status;
}
