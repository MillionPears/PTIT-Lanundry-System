package com.laundry.order_service.service.implement;

import com.laundry.order_service.dto.*;
import com.laundry.order_service.entity.Order;
import com.laundry.order_service.entity.OrderDetail;
import com.laundry.order_service.entity.OrderDetailId;
import com.laundry.order_service.exception.EntityNotFoundException;
import com.laundry.order_service.exception.GlobalCode;
import com.laundry.order_service.repository.HttpClient.ServiceClient;
import com.laundry.order_service.repository.OrderDetailRepository;
import com.laundry.order_service.repository.OrderRepository;
import com.laundry.order_service.service.OrderDetailService;
import com.laundry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ServiceClient serviceClient;
    private final OrderService orderService;
    private double calculateDiscountedPrice(double amount, double originalPrice, Long serviceId) {
//        System.out.println("amount"+ amount);
//        System.out.println("originalPrice"+ originalPrice);
        if (serviceId == 11 || serviceId == 12) {
            double discount = 0;
            if (amount > 7) {
                discount = 3000;
            } else if (amount > 5) {
                discount = 2000;
            } else if (amount > 3) {
                discount = 1000;
            }
            // Giá cuối cùng sau khi giảm
            return (originalPrice - discount) ;
        }
        // Không có giảm giá
        return originalPrice;
    }
    @Override
    public List<OrderDetailResponse> createOrderDetail(List<OrderDetailRequest> orderDetailRequests) {

        for (OrderDetailRequest x: orderDetailRequests)
        {
            ApiResponse<ServiceResponse> apiResponse = serviceClient.getServiceById(x.getId().getServiceId()).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {

                double originalPrice = apiResponse.getData().getPrice();
                x.setPrice(calculateDiscountedPrice(x.getAmount(), originalPrice, x.getId().getServiceId()));

            }
        }
        List<OrderDetail> orderDetailList = orderDetailRequests.stream()
                .map(orderDetailRequest -> {
                    OrderDetail orderDetail = new OrderDetail();
                    // Kiểm tra và ánh xạ Order từ OrderRepository
                    Order order = orderRepository.findById(orderDetailRequest.getId().getOrderId())
                            .orElseThrow(() -> new EntityNotFoundException("Order not found", GlobalCode.ERROR_ENTITY_NOT_FOUND));
                    OrderDetailId orderDetailId = new OrderDetailId();
                    orderDetailId.setOrderId(orderDetailRequest.getId().getOrderId());
                    orderDetailId.setServiceId(orderDetailRequest.getId().getServiceId());
                    modelMapper.map(orderDetailRequest, orderDetail);
                    orderDetail.setOrder(order);
                    double currentPrice = orderDetail.getPrice();
                    orderDetail.setPrice(currentPrice);

                    return orderDetail;
                })
                .collect(Collectors.toList());
            orderDetailRepository.saveAll(orderDetailList);
        //System.out.println("abcac"+orderDetailList.get(0).getOrder().getOrderId());
        orderService.updateOrderStatus(orderDetailList.get(0).getOrder().getOrderId(), 1);
        return orderDetailList.stream()
                .map(orderDetail -> {
                    OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail,OrderDetailResponse.class);

                    return orderDetailResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailResponse> getOrderDetail(Long orderId) {
        List<OrderDetailResponse> list = orderDetailRepository.findAll()
                .stream()
                .filter(orderDetail -> orderDetail.getId().getOrderId().equals(orderId))
                .map(orderDetail -> {
                    OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail,OrderDetailResponse.class);
                    ApiResponse<ServiceResponse> apiResponse = serviceClient.getServiceById(orderDetail.getId().getServiceId()).getBody();
                   // System.out.println("hahaaha"+ orderDetail.getId().getServiceId());
                    orderDetailResponse.setServiceName(apiResponse.getData().getServiceName());
                    return orderDetailResponse;
                }).collect(Collectors.toList());
        return list;
    }
}
