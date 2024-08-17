package com.laundry.customer_service.service.implement;


import com.laundry.customer_service.dto.CustomerRequest;
import com.laundry.customer_service.dto.CustomerResponse;
import com.laundry.customer_service.entity.Customer;
import com.laundry.customer_service.entity.Ranking;
import com.laundry.customer_service.exception.EntityExitsException;

import com.laundry.customer_service.exception.EntityNotFoundException;

import com.laundry.customer_service.exception.GlobalCode;
import com.laundry.customer_service.repository.CustomerRepository;
import com.laundry.customer_service.repository.RankingRepository;
import com.laundry.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final RankingRepository rankingRepository;




    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        boolean checkEmail = customerRepository.existsByEmail(customerRequest.getEmail());
        if(checkEmail){
            throw new EntityExitsException("Email đã đuược đăng ký trước đó", GlobalCode.ERROR_ID_EXIST);
        }
        boolean checkPhoneNumber = customerRepository.existsByPhoneNumber(customerRequest.getPhoneNumber());

        if(checkPhoneNumber){
            throw new EntityExitsException("Số điêện thoại đã đuược đăng ký trước đó", GlobalCode.ERROR_ID_EXIST);
        }
        Customer customer = modelMapper.map(customerRequest, Customer.class);
        Ranking ranking = rankingRepository.findById(Long.valueOf(1)).orElseThrow();
        customer.setRanking(ranking);
        customer=customerRepository.save(customer);
        CustomerResponse customerResponse= modelMapper.map(customer,CustomerResponse.class);
        customerResponse.setRankId(customer.getRanking().getRankId());
        return customerResponse;

    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest, Long customerId) {
        // Lấy thực thể hiện tại từ cơ sở dữ liệu
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new EntityNotFoundException("Customer not found with id: " + customerId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        // Cấu hình ModelMapper để chỉ sao chép các giá trị không null
        //modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        // Sao chép các giá trị từ CustomerRequest sang Customer hiện tại
        modelMapper.map(customerRequest, customer);

        // Lưu thực thể đã cập nhật vào cơ sở dữ liệu
        customer = customerRepository.save(customer);

        // Chuyển đổi thành CustomerResponse
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        customerResponse.setRankId(customer.getRanking().getRankId());
        return customerResponse;

    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> list = customerRepository.findAll();
        return list.stream()
                .map(customer -> {
                    CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
                    customerResponse.setRankId(customer.getRanking().getRankId());
                    return customerResponse;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<CustomerResponse> getCustomersByRank(Long rankid) {
        List<Customer> list = customerRepository.findAllByRankId(rankid);

        return list.stream()
                .map(customer -> {
                    CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
                    customerResponse.setRankId(customer.getRanking().getRankId());
                    return customerResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getAllCustomerSortedByNameASC() {
        List<Customer> list = customerRepository.findAllByOrderByNameAsc();
        return list.stream()
                .map(customer -> {
                    CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
                    customerResponse.setRankId(customer.getRanking().getRankId());
                    return customerResponse;
                })
                .collect(Collectors.toList());


    }

    @Override
    public CustomerResponse getCustomerByUserName(String username) {
        Customer customer = customerRepository.findByUserName(username);
        System.out.println("customer"+customer.getId());
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        customerResponse.setRankId(customer.getRanking().getRankId());
        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()-> new  EntityNotFoundException(GlobalCode.ERROR_ENTITY_NOT_FOUND,"Khong tim thay Khach Hang")
        );

        CustomerResponse customerResponse = modelMapper.map(customer,CustomerResponse.class);
        System.out.println(customerResponse.getName());
        customerResponse.setRankId(customer.getRanking().getRankId());
        return customerResponse;
    }
}
