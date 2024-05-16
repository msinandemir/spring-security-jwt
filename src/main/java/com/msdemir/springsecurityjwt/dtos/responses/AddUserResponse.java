package com.msdemir.springsecurityjwt.dtos.responses;

import lombok.Builder;

import java.time.Instant;

@Builder
public record AddUserResponse(Long id,
                              Instant createdAt,
                              Instant updatedAt,
                              String name,
                              String username) {
}
