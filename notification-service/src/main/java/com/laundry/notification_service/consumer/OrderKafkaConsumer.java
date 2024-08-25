package com.laundry.notification_service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laundry.notification_service.dto.NotificationResponse;
import com.laundry.notification_service.dto.OrderKafka;
import com.laundry.notification_service.payload.KafkaPayloadProcessor;
import com.laundry.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderKafkaConsumer {
    private final NotificationService notificationService;
    private final KafkaPayloadProcessor payloadProcessor;

    @KafkaListener(topics = "${spring.kafka.topic.order-update-status}", groupId = "kafka-order-listener")
    public void listenOrderUpdateStatus(@Payload byte[] message) {
        OrderKafka orderKafka = payloadProcessor.deserializePayload(message, OrderKafka.class);
        System.out.println("Converted OrderKafka: " + orderKafka);

        // Xử lý thông báo cho đơn hàng
        // notificationService.handleNotification(new NotificationResponse("Order status updated", "Success"));
    }
}
