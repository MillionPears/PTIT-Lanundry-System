package com.laundry.customer_service.dto;

import lombok.Data;

@Data
public class CustomerRequest {
    String name;
    String address;
    String email;
    String phoneNumber;
    String hobbie;
    String avatar;
    String username;
}
