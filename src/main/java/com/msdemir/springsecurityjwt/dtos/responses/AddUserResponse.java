package com.msdemir.springsecurityjwt.dtos.responses;

import java.time.Instant;

public record AddUserResponse(Long id,
                              Instant createdAt,
                              Instant updatedAt,
                              String name,
                              String username) {
}
