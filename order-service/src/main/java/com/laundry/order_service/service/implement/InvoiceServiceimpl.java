package com.laundry.order_service.service.implement;

import com.laundry.order_service.dto.InvoiceRequest;
import com.laundry.order_service.dto.InvoiceResponse;
import com.laundry.order_service.entity.Invoice;
import com.laundry.order_service.entity.Order;
import com.laundry.order_service.entity.OrderDetail;
import com.laundry.order_service.exception.EntityNotFoundException;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.repository.InvoiceRepository;
import com.laundry.order_service.repository.OrderDetailRepository;
import com.laundry.order_service.repository.OrderRepository;
import com.laundry.order_service.service.InvoiceService;
import com.laundry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceimpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;


    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    public double calculateTotalPrice(Order order) {
       List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);
        return orderDetails.stream()
                .mapToDouble(detail -> detail.getAmount() * detail.getPrice())
                .sum();
    }

    @Override
    public void createInvoice(Order order) {

        if (order.getStatus() != 1) { // status =1: da xac nhan don hang
            throw new IllegalStateException("Chỉ được phép tạo hóa đơn cho đơn hàng đã duoc xac nhan");
        }
        Invoice invoice = new Invoice();
        invoice.setCreatedDate(OffsetDateTime.now(ZoneOffset.UTC)); // Sử dụng múi giờ UTC hoặc OffsetDateTime với ZoneOffset cụ thể
        invoice.setTotalPrice(calculateTotalPrice(order));
        invoice.setOrder(order);
        invoice.setPaymentStatus(0);  // chua thanh toan
        invoiceRepository.save(invoice);

    }

    @Override
    public InvoiceResponse updatePaymentStatus(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow((
                () -> new EntityNotFoundException("Khong tim thay hoa don", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        ));
        invoice.setPaymentStatus(1);
        invoice=invoiceRepository.save(invoice);
        InvoiceResponse invoiceResponse = modelMapper.map(invoice,InvoiceResponse.class);
        invoiceResponse.setOrderId(invoice.getOrder().getOrderId());
        return invoiceResponse;
    }

    @Override
    public List<InvoiceResponse> getALl() {
        List<InvoiceResponse> list = invoiceRepository.findAll()
                .stream()
                .map(invoice -> {
                    InvoiceResponse invoiceResponse = modelMapper.map(invoice,InvoiceResponse.class);
                    invoiceResponse.setOrderId(invoice.getOrder().getOrderId());
                    return invoiceResponse;
                }).collect(Collectors.toList());
        return list;
    }

}
