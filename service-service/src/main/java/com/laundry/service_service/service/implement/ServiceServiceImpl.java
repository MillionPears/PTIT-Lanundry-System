package com.laundry.service_service.service.implement;

import com.laundry.service_service.dto.ServiceRequest;
import com.laundry.service_service.dto.ServiceResponse;
import com.laundry.service_service.entity.Service;
import com.laundry.service_service.exception.EntityExitsException;
import com.laundry.service_service.exception.EntityNotFoundException;
import com.laundry.service_service.exception.GlobalCode;
import com.laundry.service_service.repository.HttpClient.PromotionClient;
import com.laundry.service_service.repository.ServiceRepository;
import com.laundry.service_service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ModelMapper modelMapper;
    private final ServiceRepository serviceRepository;
    private final PromotionClient promotionClient;
    @Override
    public ServiceResponse createService(ServiceRequest serviceRequest) {
        // mapping yeu cau phai dung hoan toan ten thuoc dinh o 2 class
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        boolean checkName = serviceRepository.existsByServiceName(serviceRequest.getServiceName());
        if (checkName){
            throw  new EntityExitsException("Ten dich vu da duoc dat truoc do", GlobalCode.ERROR_ID_EXIST);
        }
        Service service = modelMapper.map(serviceRequest,Service.class);

        service = serviceRepository.save(service);
        ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
        serviceResponse.setStaffId(service.getStaffId());
        serviceResponse.setPromotionId(service.getPromotionId());
        return serviceResponse;
    }

    @Override
    public ServiceResponse updateService(ServiceRequest serviceRequest, Long id) {

        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay dich vu de chinh sua", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        if (!serviceRequest.getServiceName().equals(service.getServiceName())) {
            boolean checkName = serviceRepository.existsByServiceName(serviceRequest.getServiceName());
            if (checkName) {
                throw new EntityExitsException("Tên dich vu đã tồn tại", GlobalCode.ERROR_ID_EXIST);
            }
        }
        modelMapper.map(serviceRequest, service);

        service=serviceRepository.save(service);
        ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
        serviceResponse.setStaffId(service.getStaffId());
        serviceResponse.setPromotionId(service.getPromotionId());
        return serviceResponse;
    }

    @Override
    public List<ServiceResponse> getServiceByStatus(int status) {
        List<Service> list = serviceRepository.findAllByStatus(status);

        return list.stream()
                .map(service -> {
                    ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
                    serviceResponse.setStaffId(service.getStaffId());
                    serviceResponse.setPromotionId(service.getPromotionId());
                    return serviceResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ServiceResponse getServiceById(Long id) {
        Service service = serviceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Khong tim thay dich vu de chinh sua", GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
        serviceResponse.setStaffId(service.getStaffId());
        serviceResponse.setPromotionId(service.getPromotionId());
        double price = service.getPrice();
        if (service.getPromotionId() != null) {
            Double discountRate = promotionClient.getDiscountRate(service.getPromotionId()).getBody();
            if (discountRate != null) {
                price = price - (price * discountRate);
            }
        }
        serviceResponse.setPrice(price);

        return serviceResponse;

    }

    @Override
    public void updatePromotionIdToNull(Long promotionId) {
         serviceRepository.updatePromotionIdToNull(promotionId);
    }

    @Override
    public  List<ServiceResponse> updatePromotionIdForServices(List<Long> serviceIds, Long promotionId) {

        List<Service> services = serviceRepository.findAllById(serviceIds);
        for (Service service : services) {
            service.setPromotionId(promotionId);
        }
        services = serviceRepository.saveAll(services);
        return services.stream()
                .map(service -> {
                    ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
                    serviceResponse.setStaffId(service.getStaffId());
                    serviceResponse.setPromotionId(service.getPromotionId());
                    return serviceResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceResponse> getServiceByPromotionId(Long promotionId) {
        List<Service> list = serviceRepository.findAllByPromotionId(promotionId);

        return list.stream()
                .map(service -> {
                    ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
                    serviceResponse.setStaffId(service.getStaffId());
                    serviceResponse.setPromotionId(service.getPromotionId());
                    return serviceResponse;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<ServiceResponse> getAllService() {
        List<Service> list = serviceRepository.findAll();

        return list.stream()
                .map(service -> {
                    ServiceResponse serviceResponse = modelMapper.map(service,ServiceResponse.class);
                    serviceResponse.setStaffId(service.getStaffId());
                    serviceResponse.setPromotionId(service.getPromotionId());
                    return serviceResponse;
                })
                .collect(Collectors.toList());
    }
}
