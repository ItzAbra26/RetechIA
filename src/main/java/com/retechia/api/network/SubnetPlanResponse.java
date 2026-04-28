package com.retechia.api.network;

import java.util.List;

public record SubnetPlanResponse(
        String baseNetwork,
        List<SubnetAllocation> subnets
) {
}
