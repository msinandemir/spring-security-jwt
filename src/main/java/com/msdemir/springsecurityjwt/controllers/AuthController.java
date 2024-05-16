package com.msdemir.springsecurityjwt.controllers;

import com.msdemir.springsecurityjwt.dtos.requests.AddUserRequest;
import com.msdemir.springsecurityjwt.dtos.requests.LoginUserRequest;
import com.msdemir.springsecurityjwt.dtos.responses.AddUserResponse;
import com.msdemir.springsecurityjwt.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome public endpoint", HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<AddUserResponse> register(@RequestBody AddUserRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginUserRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }
}
