package com.laundry.order_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class InvoiceRequest {
    Long orderId;
    OffsetDateTime createdDate;
}
