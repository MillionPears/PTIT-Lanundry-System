package com.laundry.notification_service.service;


import com.laundry.notification_service.dto.NotificationResponse;

public interface NotificationService {
    void handleNotification(NotificationResponse notificationResponse);
}
