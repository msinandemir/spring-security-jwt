package com.msdemir.springsecurityjwt.services;

import com.msdemir.springsecurityjwt.dtos.requests.AddUserRequest;
import com.msdemir.springsecurityjwt.dtos.requests.LoginUserRequest;
import com.msdemir.springsecurityjwt.dtos.responses.AddUserResponse;
import com.msdemir.springsecurityjwt.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AddUserResponse register(AddUserRequest request){
        return userService.addUser(request);
    }

    public String login(LoginUserRequest request){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));
        if(authentication.isAuthenticated()){
            return jwtUtil.generateToken(request.username(), null);
        }
        throw new UsernameNotFoundException("invalid user {}" + request.username());
    }
}
