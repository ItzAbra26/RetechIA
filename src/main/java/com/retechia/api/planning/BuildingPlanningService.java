package com.retechia.api.planning;

import java.util.List;

import org.springframework.stereotype.Service;

import com.retechia.api.network.DepartmentRequirement;
import com.retechia.api.network.SubnetPlanRequest;
import com.retechia.api.network.SubnetPlanResponse;
import com.retechia.api.network.SubnetPlannerService;

@Service
public class BuildingPlanningService {

    private final SubnetPlannerService subnetPlannerService;

    public BuildingPlanningService(SubnetPlannerService subnetPlannerService) {
        this.subnetPlannerService = subnetPlannerService;
    }

    public BuildingProposalResponse createProposal(BuildingProposalRequest request) {
        int totalHosts = request.departments().stream()
                .mapToInt(DepartmentRequirement::hosts)
                .sum();

        int estimatedNetworkPoints = (int) Math.ceil(totalHosts * 1.25);
        int recommendedSwitches = Math.max(1, (int) Math.ceil(estimatedNetworkPoints / 24.0));
        int recommendedRacks = Math.max(1, request.floors());

        SubnetPlanResponse subnetPlan = subnetPlannerService.plan(
                new SubnetPlanRequest(request.baseNetwork(), request.departments())
        );

        List<String> recommendations = List.of(
                "Separar la red por departamentos para mejorar seguridad y mantenimiento.",
                "Reservar una VLAN independiente para servidores y servicios criticos.",
                "Instalar al menos un rack por planta si el edificio tiene cableado distribuido.",
                "Documentar tomas de red, patch panels, switches y rutas de cableado desde el primer dia.",
                "Preparar una version de mantenimiento para que el cliente pueda consultar incidencias."
        );

        return new BuildingProposalResponse(
                request.buildingName(),
                request.floors(),
                recommendedRacks,
                recommendedSwitches,
                estimatedNetworkPoints,
                recommendations,
                subnetPlan.subnets()
        );
    }
}
