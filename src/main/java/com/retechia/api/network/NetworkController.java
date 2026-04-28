package com.retechia.api.network;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/network")
public class NetworkController {

    private final SubnetPlannerService subnetPlannerService;

    public NetworkController(SubnetPlannerService subnetPlannerService) {
        this.subnetPlannerService = subnetPlannerService;
    }

    @PostMapping("/subnets")
    public SubnetPlanResponse calculateSubnets(@Valid @RequestBody SubnetPlanRequest request) {
        return subnetPlannerService.plan(request);
    }
}
