package com.laundry.order_service.kafka.producer;


import com.laundry.order_service.dto.OrderResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@Component
@RequiredArgsConstructor
public class OrderKafkaProducer {
    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;

    @Value("${spring.kafka.topic.order-update-status}")
    private String topic;

    @Value("${spring.kafka.replication.factor:1}")
    private int replicationFactor;

    @Value("${spring.kafka.partition.number:1}")
    private int partitionNumber;

    public void writeToKafka(String uuid, OrderResponse orderResponse) {
        kafkaTemplate.send(topic, uuid, orderResponse);
    }

    @Bean
    @Order(-1)
    public NewTopic createNewTopic() {
        return new NewTopic(topic, partitionNumber, (short) replicationFactor);
    }
}
