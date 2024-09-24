package com.hoadri.retro.dtos;

import jakarta.validation.constraints.NotEmpty;

public record UserPublicDTO(
        @NotEmpty String username,
        String profilePicturePath,
        String description) {
}