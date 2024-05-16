package com.msdemir.springsecurityjwt.dtos.requests;

public record AddUserRequest(String name,
                             String username,
                             String password) {
}
