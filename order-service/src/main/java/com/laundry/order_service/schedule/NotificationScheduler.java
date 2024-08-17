package com.laundry.order_service.schedule;

import com.laundry.order_service.entity.Order;
import com.laundry.order_service.repository.OrderRepository;
import com.laundry.order_service.service.EmailService;
import com.laundry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final OrderRepository orderRepository;
    private final EmailService emailService;
    private final OrderService orderService;


    @Scheduled(cron = "0 * * * * *") // Lập lịch chạy mỗi phút
    public void sendNotifications() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime twoHoursBefore = now.minusHours(2);
        LocalDateTime twoHoursAfter = now.plusHours(2);

// Lấy tất cả các đơn hàng có thời gian đặt hàng trước thời gian hiện tại 2 giờ và sau thời gian hiện tại 2 giờ
        List<Order> orders = orderRepository.findOrdersByOrderDateBetween(twoHoursBefore, twoHoursAfter);

        for (Order order : orders) {
           //System.out.println("order:  "+order.getOrderId());
            if (!order.isNotified()) { // Kiểm tra nếu đơn hàng chưa được thông báo
                System.out.println("haha 1");
                LocalDateTime orderDateTime = order.getOrderDate(); // Thời gian đặt hàng
                LocalDateTime notificationTime = orderDateTime.minusHours(2); // Thời gian thông báo: 2 giờ trước thời gian đặt hàng
                Duration timeDifference = Duration.between(now, notificationTime);

                // Kiểm tra nếu thời gian hiện tại nằm trong khoảng 2 giờ trước thời gian đặt hàng
                if (timeDifference.isNegative() && timeDifference.toHours() > -2) {
                    emailService.sendEmail(order.getEmail(),
                            "Thông báo về lịch hẹn",
                            "Lịch hẹn của bạn sắp đến thời gian. Vui lòng giao hàng để tiến hành giặt đồ.");

                    // Cập nhật trạng thái thông báo
                    orderRepository.updateNotifiedStatus(order.getOrderId());
                }
            }
            // Kiểm tra nếu đã quá 2 giờ kể từ thời gian đặt hàng và trạng thái đơn hàng là 0
            Duration timeSinceOrder = Duration.between(order.getOrderDate(), now);
            //System.out.println(timeSinceOrder.toString());
            if (timeSinceOrder.toMinutes() > 118 && order.getStatus() == 0) {
                //System.out.println("haha 2");
                emailService.sendEmail(order.getEmail(),
                        "Thông báo về lịch hẹn",
                        "Đã hủy đơn hàng vì quá thời gian giao đồ. Cảm ơn quý khách");
                orderService.deleteOrder(order.getOrderId()); // Gọi hàm hủy đơn hàng
            }
        }
    }
}

