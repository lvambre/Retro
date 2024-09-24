package com.hoadri.retro.dtos;

import com.hoadri.retro.models.Item;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UserCredentialsDTO(
        @NotEmpty String username,
        @NotEmpty String password,
        @NotEmpty String email,
        @NotEmpty String phoneNumber,
        @NotEmpty String address,
        String profilePicturePath,
        String description,
        List<Item> itemsOrdered,
        List<Item> itemsSold) {
}
