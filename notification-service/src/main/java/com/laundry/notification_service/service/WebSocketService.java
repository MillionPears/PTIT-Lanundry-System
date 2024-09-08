package com.laundry.notification_service.service;

import com.laundry.notification_service.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendOrderUpdate(OrderResponse orderResponse) {
        String destination = "/topic/order-updates";
        messagingTemplate.convertAndSend(destination, orderResponse);
    }
}
