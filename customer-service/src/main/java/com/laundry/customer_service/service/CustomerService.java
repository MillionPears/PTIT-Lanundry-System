package com.laundry.customer_service.service;

import com.laundry.customer_service.dto.CustomerRequest;
import com.laundry.customer_service.dto.CustomerResponse;


import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse updateCustomer(CustomerRequest customerRequest,Long customerId);
    List<CustomerResponse> getAllCustomer();
    List<CustomerResponse> getAllCustomerSortedByNameASC();
    List<CustomerResponse> getCustomersByRank(Long Rankid);

}
