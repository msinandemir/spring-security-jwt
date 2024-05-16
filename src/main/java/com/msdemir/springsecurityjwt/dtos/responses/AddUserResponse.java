package com.msdemir.springsecurityjwt.dtos.responses;

import com.msdemir.springsecurityjwt.models.Role;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record AddUserResponse(Long id,
                              Instant createdAt,
                              Instant updatedAt,
                              String name,
                              String username,
                              List<Role> authorities
) {
}
