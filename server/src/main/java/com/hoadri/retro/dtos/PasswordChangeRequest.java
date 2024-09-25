package com.hoadri.retro.dtos;

import jakarta.validation.constraints.NotEmpty;

public record PasswordChangeRequest(
        @NotEmpty String oldPassword,
        @NotEmpty String newPassword
) {
}
