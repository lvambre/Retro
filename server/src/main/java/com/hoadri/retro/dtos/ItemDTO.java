package com.hoadri.retro.dtos;

import com.hoadri.retro.models.enums.*;

import java.util.List;
import java.util.UUID;

public record ItemDTO(
        UUID id,
        String name,
        String description,
        String username,
        double price,
        boolean available,
        boolean women,
        boolean men,
        Brand brand,
        Category category,
        List<Color> colors,
        Condition condition,
        Size size,
        List<String> imagePaths
) {
}
