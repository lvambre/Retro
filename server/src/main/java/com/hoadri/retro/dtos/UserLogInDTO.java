package com.hoadri.retro.dtos;

import jakarta.validation.constraints.NotEmpty;

public record UserLogInDTO(
        @NotEmpty String username,
        @NotEmpty String password) {
}
