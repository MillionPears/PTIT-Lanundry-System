package com.laundry.warehouse_service.service.implement;

import com.laundry.warehouse_service.dto.PurchaseRequest;
import com.laundry.warehouse_service.dto.PurchaseResponse;
import com.laundry.warehouse_service.entity.Purchase;
import com.laundry.warehouse_service.repository.PurchaseRepository;
import com.laundry.warehouse_service.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceimpl implements PurchaseService {
    private final ModelMapper modelMapper;
    private final PurchaseRepository purchaseRepository;

    @Override
    public Purchase createPurchase(Long staffId) {

        Purchase purchase = new Purchase();
        purchase.setPurchaseId(null);
        purchase.setStaffId(staffId);
        purchase.setDateCreate(LocalDate.now());
        purchase = purchaseRepository.save(purchase);

//        PurchaseResponse purchaseResponse = modelMapper.map(purchase, PurchaseResponse.class);
//        purchaseResponse.setPurchaseId(purchase.getPurchaseId());
        return purchase;
    }

    @Override
    public List<PurchaseResponse> getAll() {
        List<PurchaseResponse> list = purchaseRepository.findAll()
                .stream()
                .map(purchase -> {
                    PurchaseResponse purchaseResponse = modelMapper.map(purchase,PurchaseResponse.class);
                    return purchaseResponse;
                }).collect(Collectors.toList());
        return list;
    }
}
