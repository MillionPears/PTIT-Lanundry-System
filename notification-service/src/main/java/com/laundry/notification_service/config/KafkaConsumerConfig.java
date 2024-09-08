package com.laundry.notification_service.config;


import com.laundry.notification_service.dto.OrderKafka;
import com.laundry.notification_service.dto.OrderResponse;
import com.laundry.notification_service.exception.KafkaErrorHandler;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value(value="${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, OrderResponse> OrderConsumerFactory()
    {

        // Creating a map of string-object type
        Map<String, Object> config = new HashMap<>();

        // Adding the Configuration
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,
                "kafka-order-listener");
        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Ensure trusted packages are configured
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, OrderResponse.class.getName());

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(OrderResponse.class));
    }
    // Creating a Listener
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderResponse> bookListener()
    {
        ConcurrentKafkaListenerContainerFactory<
                String, OrderResponse> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(OrderConsumerFactory());
        return factory;
    }
}
