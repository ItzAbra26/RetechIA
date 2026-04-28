package com.retechia.api.network;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DepartmentRequirement(
        @NotBlank String name,
        @Min(1) int hosts
) {
}
