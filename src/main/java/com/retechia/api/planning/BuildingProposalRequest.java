package com.retechia.api.planning;

import java.util.List;

import com.retechia.api.network.DepartmentRequirement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record BuildingProposalRequest(
        @NotBlank String buildingName,
        @Min(1) int floors,
        @NotBlank String baseNetwork,
        @NotEmpty List<@Valid DepartmentRequirement> departments
) {
}
