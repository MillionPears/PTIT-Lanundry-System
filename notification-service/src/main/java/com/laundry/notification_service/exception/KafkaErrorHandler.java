package com.laundry.notification_service.exception;



import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaErrorHandler implements CommonErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(KafkaErrorHandler.class);

    @Override
    public boolean handleOne(Exception exception, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        handle(exception, consumer);
        return true;  // hoặc return false tùy thuộc vào việc bạn muốn tiếp tục xử lý hay không
    }

    @Override
    public void handleOtherException(Exception exception, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        handle(exception, consumer);
    }

    private void handle(Exception exception, Consumer<?, ?> consumer) {
        log.error("Exception thrown", exception);
        // Add your custom handling logic here
    }
}

