package com.msdemir.springsecurityjwt.dtos.responses;

import java.time.Instant;

public record UserAddResponse(Long id,
                              Instant createdAt,
                              Instant updatedAt,
                              String name,
                              String username) {
}
