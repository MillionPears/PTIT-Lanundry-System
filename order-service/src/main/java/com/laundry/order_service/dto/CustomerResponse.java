package com.laundry.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerResponse {
    Long id;
    String name;
    String address;
    String email;
    String phoneNumber;
    String hobbie;
    String avatar;
    String username;

    Long rankId;
}
