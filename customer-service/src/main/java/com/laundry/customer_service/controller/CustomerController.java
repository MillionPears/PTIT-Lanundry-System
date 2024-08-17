package com.laundry.customer_service.controller;

import com.laundry.customer_service.dto.ApiResponse;
import com.laundry.customer_service.dto.CustomerRequest;
import com.laundry.customer_service.dto.CustomerResponse;
import com.laundry.customer_service.exception.GlobalCode;
import com.laundry.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/customer/")
public class CustomerController {
        private final CustomerService customerService;

        @PostMapping("create")
        public ResponseEntity<ApiResponse<CustomerResponse>>createCustomer(@RequestBody CustomerRequest customerRequest){
            CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
            ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Tạo thông tin tài khoản thành công", customerResponse);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

    @PutMapping("update/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @RequestBody CustomerRequest customerRequest,
            @PathVariable Long customerId) {
        CustomerResponse customerResponse = customerService.updateCustomer(customerRequest, customerId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Cập nhật thành công", customerResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/all")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>>getAllCustomer(){
        List<CustomerResponse> list = customerService.getAllCustomer();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Lay danh sach khach hang thanh cong", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/byName")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>>getAllCustomerSortedByNameASC(){
        List<CustomerResponse> list = customerService.getAllCustomerSortedByNameASC();
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Danh sach khach hang sap xep theo ten", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("list/byrank/{rankId}")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>>getCustomersByRank(@PathVariable Long rankId){
        List<CustomerResponse> list = customerService.getCustomersByRank(rankId);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Danh sach khach hang sap xep theo rank", list);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("getbyusername/{username}")
    public ResponseEntity<ApiResponse<CustomerResponse>>getCustomer(@PathVariable String username){
        CustomerResponse customerResponse = customerService.getCustomerByUserName(username);
        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Thong tin khach hang", customerResponse);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("getbyid/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>>getCustomerById(@PathVariable Long id){
        CustomerResponse customerResponse = customerService.getCustomerById(id);

        ApiResponse apiResponse = new ApiResponse(GlobalCode.SUCCESS, "Thong tin khach hang", customerResponse);

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
