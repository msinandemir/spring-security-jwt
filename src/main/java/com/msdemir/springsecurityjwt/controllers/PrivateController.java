package com.msdemir.springsecurityjwt.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/private")
public class PrivateController {

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/user")
    public ResponseEntity<String> user() {
        return new ResponseEntity<>("User endpoint", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return new ResponseEntity<>("Admin endpoint", HttpStatus.OK);
    }
}
