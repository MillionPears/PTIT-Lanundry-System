package com.laundry.service_service.dto;

import lombok.Data;

import java.util.List;


@Data
public class ServiceIdRequest {
    private List<Long> serviceIds;
}
