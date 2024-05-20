package com.msdemir.springsecurityjwt.services;

import com.msdemir.springsecurityjwt.dtos.requests.AddUserRequest;
import com.msdemir.springsecurityjwt.dtos.requests.LoginUserRequest;
import com.msdemir.springsecurityjwt.dtos.responses.AddUserResponse;
import com.msdemir.springsecurityjwt.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {
    @Mock
    UserService userService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    Authentication authentication;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    AuthService cut;

    @Test
    @DisplayName("Test register method should user register and return saved user")
    void should_user_register_and_return_saved_user() {
        AddUserResponse addUserResponse = AddUserResponse.builder()
                .id(1L)
                .name("test-name")
                .username("test")
                .authorities(new ArrayList<>())
                .build();
        AddUserRequest addUserRequest = new AddUserRequest("test-name", "test", "123");

        when(userService.addUser(any(AddUserRequest.class))).thenReturn(addUserResponse);

        AddUserResponse registeredUser = cut.register(addUserRequest);

        assertNotNull(registeredUser);
        assertEquals("test-name", registeredUser.name());
        assertEquals("test", registeredUser.username());

        verify(userService, times(1)).addUser(addUserRequest);
    }

    @Test
    @DisplayName("Test login method should user login and return jwt token")
    void should_user_login_and_return_jwt_token() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "123");

        when(authentication.isAuthenticated()).thenReturn(true);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken(loginUserRequest.username(), null)).thenReturn("jwt-token");

        String jwtToken = cut.login(loginUserRequest);

        assertEquals("jwt-token", jwtToken);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(loginUserRequest.username(), null);
    }

    @Test
    @DisplayName("Test login method should user failed login and throw exception")
    void should_user_failed_login_and_throw_exception() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("test", "123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException("invalid user " + loginUserRequest.username()));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> cut.login(loginUserRequest));

        assertEquals("invalid user " + loginUserRequest.username(), exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(0)).generateToken(loginUserRequest.username(), null);
    }
}
