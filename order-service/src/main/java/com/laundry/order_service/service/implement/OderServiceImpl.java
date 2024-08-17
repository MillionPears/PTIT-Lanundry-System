package com.laundry.order_service.service.implement;


import com.laundry.order_service.dto.ApiResponse;
import com.laundry.order_service.dto.CustomerResponse;
import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;
import com.laundry.order_service.entity.Delivery_Type;
import com.laundry.order_service.entity.Order;
import com.laundry.order_service.entity.OrderDetail;
import com.laundry.order_service.exception.EntityNotFoundException;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.repository.Delivery_TypeRepository;
import com.laundry.order_service.repository.HttpClient.CustomerClient;
import com.laundry.order_service.repository.OrderDetailRepository;
import com.laundry.order_service.repository.OrderRepository;
import com.laundry.order_service.service.InvoiceService;
import com.laundry.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final Delivery_TypeRepository deliveryTypeRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final InvoiceService invoiceService;
    private final CustomerClient customerClient;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Order order = modelMapper.map(orderRequest, Order.class);
        Delivery_Type delivery_type = deliveryTypeRepository.findById(orderRequest.getDeliveryTypeId()).orElseThrow(
                ()-> new EntityNotFoundException("Loại giao hàng không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        if(delivery_type.getDeliveryId()==1){ // nhân truc tiep thi set trạng thái là khong giao
            order.setDeliveryStatus(0);
        }else order.setDeliveryStatus(1);
        order.setDeliveryType(delivery_type);
        order.setNotified(false);
        order= orderRepository.save(order);

        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
        return orderResponse;
    }

    @Override
    public OrderResponse updateDeliveryStatus(Long id, int status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay thong tin don hang",GlobalCode.ERROR_ENTITY_NOT_FOUND)

        );

        Delivery_Type delivery_type = deliveryTypeRepository.findById(order.getDeliveryType().getDeliveryId()).orElseThrow(
                ()-> new EntityNotFoundException("Loại giao hàng không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        // nếu là nhận hàng trực tiếp thì deliveryStatus =0
        if(delivery_type.getDeliveryId()==1){
            order.setDeliveryStatus(0);
        } else order.setDeliveryStatus(status);
        order=orderRepository.save(order);
        OrderResponse orderResponse = modelMapper.map(order,OrderResponse.class);
        orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
        return orderResponse;
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, int status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay thong tin don hang",GlobalCode.ERROR_ENTITY_NOT_FOUND)

        );
        order.setStatus(status);
        order=orderRepository.save(order);
        if(order.getStatus()==1)
        {
            invoiceService.createInvoice(order);
        }
        OrderResponse orderResponse = modelMapper.map(order,OrderResponse.class);
        orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
        return orderResponse;
    }

    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay thong tin don hang",GlobalCode.ERROR_ENTITY_NOT_FOUND)

        );

        if (order.getStatus() != 0) {
            throw new EntityNotFoundException("Không thể xóa đơn hàng trong quá trình giặt",GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }
        orderRepository.deleteById(id);
        return "Id don hang da xoa "+id;
    }

    @Override
    public String updateDeliveryType(Long id, Long type) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay thong tin don hang",GlobalCode.ERROR_ENTITY_NOT_FOUND)

        );
        Delivery_Type delivery_type = deliveryTypeRepository.findById(type).orElseThrow(
                ()-> new EntityNotFoundException("Loại giao hàng không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        order.setDeliveryType(delivery_type);
        order=orderRepository.save(order);
        Long result = order.getDeliveryType().getDeliveryId();
        String deliveryTypeName = result == 1 ? "Giao tận nơi" : (result == 2 ? "Nhận đồ trực tiếp" : "Unknown");
        return deliveryTypeName;


    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        List<OrderResponse> list = orderRepository.findAll()
                .stream().filter(order -> order.getCustomerId() == customerId).map(
                order -> {
                    OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
                    orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
                    ApiResponse<CustomerResponse> apiResponse = customerClient.getCustomerById(customerId).getBody();

                    orderResponse.setCustomerName(apiResponse.getData().getName());
                            return orderResponse;
                }
        ).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<OrderResponse> getOrderByStatus(int status) {
        List<OrderResponse> list = orderRepository.findAll()
                .stream()
                .filter(order -> order.getStatus()==status)
                .map(order -> {
                    OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
                    orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
                    ApiResponse<CustomerResponse> apiResponse = customerClient.getCustomerById(order.getCustomerId()).getBody();
                    orderResponse.setCustomerName(apiResponse.getData().getName());
                    return orderResponse;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        List<OrderResponse> list = orderRepository.findAll()
                .stream()
                .map(order -> {
                    OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
                    orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
                    ApiResponse<CustomerResponse> apiResponse = customerClient.getCustomerById(order.getCustomerId()).getBody();
                    orderResponse.setCustomerName(apiResponse.getData().getName());
                    return orderResponse;
                }).collect(Collectors.toList());
        return list;
    }
    @Override
    public List<OrderResponse> getOrderShipment() {

            List<OrderResponse> list = orderRepository.findAll()
                    .stream()
                    .filter(order -> order.getStatus() == 3 &&
                            order.getDeliveryType() != null &&
                            order.getDeliveryType().getDeliveryId() == 2)
                    .map(order -> {
                        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
                        orderResponse.setDeliveryTypeId(order.getDeliveryType().getDeliveryId());
                        ApiResponse<CustomerResponse> apiResponse = customerClient.getCustomerById(order.getCustomerId()).getBody();
                        orderResponse.setCustomerName(apiResponse.getData().getName());
                        return orderResponse;
                    }).collect(Collectors.toList());
            return list;

    }
}
