package com.laundry.notification_service.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class KafkaPayloadProcessor {

    private final ObjectMapper objectMapper;

    public KafkaPayloadProcessor() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Đăng ký module hỗ trợ cho các kiểu dữ liệu ngày giờ
    }

    public <T> T deserializePayload(byte[] message, Class<T> clazz) {
        String json = new String(message, StandardCharsets.UTF_8);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing message", e);
        }
    }
}
