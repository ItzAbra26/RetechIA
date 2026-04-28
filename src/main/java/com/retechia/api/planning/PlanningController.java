package com.retechia.api.planning;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    private final BuildingPlanningService buildingPlanningService;

    public PlanningController(BuildingPlanningService buildingPlanningService) {
        this.buildingPlanningService = buildingPlanningService;
    }

    @PostMapping("/building-proposal")
    public BuildingProposalResponse createProposal(@Valid @RequestBody BuildingProposalRequest request) {
        return buildingPlanningService.createProposal(request);
    }
}
