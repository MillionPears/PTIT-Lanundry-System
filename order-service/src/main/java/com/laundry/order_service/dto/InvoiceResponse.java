package com.laundry.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    Long orderId;
    Long invoiceId;
    double totalPrice;
    OffsetDateTime createdDate;
    int paymentStatus;
}
