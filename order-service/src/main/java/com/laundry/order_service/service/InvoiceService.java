package com.laundry.order_service.service;

import com.laundry.order_service.dto.InvoiceRequest;
import com.laundry.order_service.dto.InvoiceResponse;
import com.laundry.order_service.entity.Order;

import java.util.List;

public interface InvoiceService {
    void createInvoice(Order order);
    InvoiceResponse updatePaymentStatus(Long id);
    List<InvoiceResponse> getALl();
}
