package com.hoadri.retro.dtos;

import jakarta.validation.constraints.NotEmpty;

public record UserCredentialsDTO(
        @NotEmpty String username,
        @NotEmpty String password,
        @NotEmpty String email,
        @NotEmpty String phoneNumber,
        @NotEmpty String address,
        String profilePicturePath,
        String description) {
}
