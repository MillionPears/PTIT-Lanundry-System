package com.laundry.notification_service.service.implement;


import com.laundry.notification_service.dto.NotificationResponse;
import com.laundry.notification_service.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void handleNotification(NotificationResponse notificationResponse) {
        System.out.println("Received notification: " + notificationResponse.getMessage());

    }
}
