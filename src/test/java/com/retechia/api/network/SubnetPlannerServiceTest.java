package com.retechia.api.network;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

class SubnetPlannerServiceTest {

    private final SubnetPlannerService service = new SubnetPlannerService();

    @Test
    void createsSubnetPlanOrderedByLargestDepartment() {
        SubnetPlanResponse response = service.plan(new SubnetPlanRequest(
                "192.168.10.0/24",
                List.of(
                        new DepartmentRequirement("Administracion", 28),
                        new DepartmentRequirement("Urgencias", 52),
                        new DepartmentRequirement("Servidores", 12)
                )
        ));

        assertThat(response.subnets()).hasSize(3);
        assertThat(response.subnets().get(0).department()).isEqualTo("Urgencias");
        assertThat(response.subnets().get(0).cidr()).isEqualTo(26);
        assertThat(response.subnets().get(0).usableHosts()).isEqualTo(62);
    }

    @Test
    void rejectsPlansThatDoNotFitInBaseNetwork() {
        SubnetPlanRequest request = new SubnetPlanRequest(
                "192.168.20.0/28",
                List.of(
                        new DepartmentRequirement("Aula 1", 12),
                        new DepartmentRequirement("Aula 2", 12)
                )
        );

        assertThatThrownBy(() -> service.plan(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no tiene espacio suficiente");
    }
}
