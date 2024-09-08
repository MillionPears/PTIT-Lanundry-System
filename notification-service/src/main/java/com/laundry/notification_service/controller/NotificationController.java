package com.laundry.notification_service.controller;

import com.laundry.notification_service.dto.OrderResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class NotificationController {
    // Endpoint cho WebSocket để nhận và gửi tin nhắn
    @MessageMapping("/order-updates")
    @SendTo("/topic/order-updates")
    public OrderResponse sendOrderUpdate(OrderResponse orderResponse) {
        return orderResponse;
    }
}
