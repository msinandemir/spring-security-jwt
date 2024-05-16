package com.msdemir.springsecurityjwt.dtos.requests;

public record LoginUserRequest(String username,
                               String password) {
}
