package com.laundry.customer_service.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerResponse {
    Long customerId;
    String name;
    String address;
    String email;
    String phoneNumber;
    String hobbie;
    String avartar;
    String username;

    Long rankId;
}
