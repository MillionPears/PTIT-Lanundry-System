package com.laundry.user_service.service.implement;


import com.laundry.user_service.dto.*;

import com.laundry.user_service.entity.Account;
import com.laundry.user_service.entity.Role;
import com.laundry.user_service.exception.*;
import com.laundry.user_service.repository.AccountRepository;
import com.laundry.user_service.repository.HttpClient.CustomerClient;
import com.laundry.user_service.repository.HttpClient.StaffClient;
import com.laundry.user_service.repository.RoleRepository;
import com.laundry.user_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;
    private final StaffClient staffClient;

    protected AccountResponse convertToAccountResponse(Account account) {

        return new AccountResponse(account.getUsername(), "",null);
    }


    @Override
    public AccountResponse registerAccount(RegisterRequest registerRequest) {
        boolean checkUsername = accountRepository.existsByUsername(registerRequest.getUsername());
        Role role = roleRepository.findById(registerRequest.getRole()).orElseThrow(
                () -> new EntityNotFoundException("Chức vụ không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if (checkUsername) {
            throw new EntityExitsException("Tài khoản đã đăng kí trước đó", GlobalCode.ERROR_NAME_EXIST);
        }
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(encoder.encode(registerRequest.getPassword()));
        //account.setPassword(registerRequest.getPassword());
        account.setRole(role);
        account.setActive(true);
        account = accountRepository.save(account);
        return convertToAccountResponse(account);
    }

    @Override
    public Account getAccount(String username) {
        Account user = accountRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản", GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if (!user.isActive()) {
            throw new EntityNotFoundException("Tài khoản bị khóa", GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }
        return user;
    }

    @Override
    public Long getRoleIdByUsername(String username) {
        return accountRepository.findRoleIdByUsername(username);
    }

    @Override
    public UserResponse getUserByUserName(String username) {
        Long roleid = this.getRoleIdByUsername(username);


        if (roleid == null) {
            throw new EntityNotFoundException("Khoong cos role ", GlobalCode.ERROR_ENTITY_NOT_FOUND);
        } else if (roleid == 1) {
            ApiResponse<CustomerResponse> apiResponse = customerClient.getCustomerByUserName(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return UserResponse.builder()
                        .data(apiResponse.getData())
                        .build();
            }
        } else {
            ApiResponse<StaffResponse> apiResponse = staffClient.getStaffByUsername(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return UserResponse.builder()
                        .data(apiResponse.getData())
                        .build();

            }
        }
        return null;
    }

    @Override
    public Long getUserIdByUserName(String username) {
        Long roleid = this.getRoleIdByUsername(username);
        if (roleid == null) {
            throw new EntityNotFoundException("Khoong cos role ", GlobalCode.ERROR_ENTITY_NOT_FOUND);
        } else if (roleid == 1) {
            ApiResponse<CustomerResponse> apiResponse = customerClient.getCustomerByUserName(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                return apiResponse.getData().getId();
            }
        } else {
            System.out.println("1");
            ApiResponse<StaffResponse> apiResponse = staffClient.getStaffByUsername(username).getBody();
            if (apiResponse != null && apiResponse.getData() != null) {
                System.out.println("2");
                System.out.println("3"+apiResponse.getData().getId());
                return apiResponse.getData().getId();

            }
        }
        return null;
    }
}
