package com.laundry.service_service.service;

import com.laundry.service_service.dto.ServiceRequest;
import com.laundry.service_service.dto.ServiceResponse;

import java.util.List;

public interface ServiceService {
    ServiceResponse createService(ServiceRequest serviceRequest);
    ServiceResponse updateService(ServiceRequest serviceRequest, Long id);
    List<ServiceResponse> getServiceByStatus(int status);
    ServiceResponse getServiceById(Long id);
    void updatePromotionIdToNull(Long promotionId);
    List<ServiceResponse> updatePromotionIdForServices(List<Long> serviceIds, Long promotionId);
    List<ServiceResponse> getServiceByPromotionId(Long promotionId);
    List<ServiceResponse> getAllService();
}
