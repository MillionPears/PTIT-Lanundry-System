package com.laundry.user_service.service.implement;


import com.laundry.user_service.dto.AccountResponse;
import com.laundry.user_service.dto.RegisterRequest;
import com.laundry.user_service.dto.UserResponse;
import com.laundry.user_service.entity.Account;
import com.laundry.user_service.entity.Role;
import com.laundry.user_service.exception.*;
import com.laundry.user_service.repository.AccountRepository;
import com.laundry.user_service.repository.RoleRepository;
import com.laundry.user_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    protected AccountResponse convertToAccountResponse(Account account){

        return new AccountResponse(account.getUsername());
    }
    protected UserResponse convertToUserResponse(Account user){
        return new UserResponse(user.getUsername(), user.getPassword(),user.getRole().getRoleName(),user.isActive());
    }

    @Override
    public AccountResponse registerAccount(RegisterRequest registerRequest){
        boolean checkUsername = accountRepository.existsByUsername(registerRequest.getUsername());
        Role role = roleRepository.findById(registerRequest.getRole()).orElseThrow(
                () -> new EntityNotFoundException("Chức vụ không tồn tại", GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if(checkUsername){
            throw new EntityExitsException("Tài khoản đã đăng kí trước đó", GlobalCode.ERROR_NAME_EXIST);
        }
        Account account = new Account();
        account.setUsername(registerRequest.getUsername());
        account.setPassword(encoder.encode(registerRequest.getPassword()));
        //account.setPassword(registerRequest.getPassword());
        account.setRole(role);
        account.setActive(true);
        account=accountRepository.save(account);
        return convertToAccountResponse(account);
    }

    @Override
    public Account getAccount(String username) {
        Account user = accountRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản",GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if(!user.isActive()){
            throw new EntityNotFoundException("Tài khoản bị khóa",GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }
        return user;
    }

}
