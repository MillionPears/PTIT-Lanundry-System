package com.laundry.user_service.config_security;



import com.laundry.user_service.entity.Account;
import com.laundry.user_service.exception.EntityNotFoundException;
import com.laundry.user_service.exception.GlobalCode;
import com.laundry.user_service.repository.AccountRepository;
import com.laundry.user_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //ApiResponse<Account> apiResponse = accountService.getAccount(username).getBody();
//        Account user = accountService.getAccount(username);
        Account user = accountRepository.findById(username).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy tài khoản", GlobalCode.ERROR_ENTITY_NOT_FOUND));
        if(!user.isActive()){
            throw new EntityNotFoundException("Tài khoản bị khóa",GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }

        return new CustomUserDetails(user);
    }
}