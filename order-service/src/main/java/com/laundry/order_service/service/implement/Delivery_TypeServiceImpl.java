package com.laundry.order_service.service.implement;

import com.laundry.order_service.entity.Delivery_Type;
import com.laundry.order_service.repository.Delivery_TypeRepository;
import com.laundry.order_service.service.Delivery_TypeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Delivery_TypeServiceImpl implements Delivery_TypeService {
    private final Delivery_TypeRepository deliveryTypeRepository;



    @PostConstruct
    public void init() {
        // Kiểm tra bảng có dữ liệu hay không trước khi thêm mới
        if (deliveryTypeRepository.count() == 0) {
            Delivery_Type deliveryType2 = new Delivery_Type(null, "Nhận hàng trực tiếp");
            Delivery_Type deliveryType1 = new Delivery_Type(null, "Giao tận nơi");


            deliveryTypeRepository.save(deliveryType1);
            deliveryTypeRepository.save(deliveryType2);
        }
    }
}
