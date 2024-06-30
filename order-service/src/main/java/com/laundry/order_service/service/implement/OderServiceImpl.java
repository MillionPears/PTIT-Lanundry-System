package com.laundry.order_service.service.implement;


import com.laundry.order_service.dto.OrderRequest;
import com.laundry.order_service.dto.OrderResponse;
import com.laundry.order_service.entity.Delivery_Type;
import com.laundry.order_service.entity.Order;
import com.laundry.order_service.exception.EntityNotFoundException;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.repository.Delivery_TypeRepository;
import com.laundry.order_service.repository.OrderRepository;
import com.laundry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final Delivery_TypeRepository deliveryTypeRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Order order = modelMapper.map(orderRequest, Order.class);
        Delivery_Type delivery_type = deliveryTypeRepository.findById(orderRequest.getDelivery_TypeId()).orElseThrow(
                ()-> new EntityNotFoundException("Loại giao hàng không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        order.setDeliveryType(delivery_type);
        order= orderRepository.save(order);

        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setOrderId(order.getOrderId());
        return orderResponse;
    }

}
