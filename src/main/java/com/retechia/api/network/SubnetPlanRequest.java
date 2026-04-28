package com.retechia.api.network;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SubnetPlanRequest(
        @NotBlank String baseNetwork,
        @NotEmpty List<@Valid DepartmentRequirement> departments
) {
}
