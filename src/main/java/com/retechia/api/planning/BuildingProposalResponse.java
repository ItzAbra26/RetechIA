package com.retechia.api.planning;

import java.util.List;

import com.retechia.api.network.SubnetAllocation;

public record BuildingProposalResponse(
        String buildingName,
        int floors,
        int recommendedRacks,
        int recommendedSwitches,
        int estimatedNetworkPoints,
        List<String> recommendations,
        List<SubnetAllocation> subnetPlan
) {
}
