package com.laundry.user_service.service.implement;


import com.laundry.user_service.dto.LoginRequest;
import com.laundry.user_service.dto.TokenResponse;
import com.laundry.user_service.security.JwtHelper;
import com.laundry.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtHelper jwtHelper;

    private final AuthenticationManager authenticationManager;
    @Override
    public TokenResponse createToken(String username,  String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        String jwt = jwtHelper.generateJwtToken(authentication);

        return new TokenResponse(jwt);
    }


}
