package com.retechia.api.network;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class SubnetPlannerServiceTest {

    private final SubnetPlannerService service = new SubnetPlannerService();

    @Test
    @Timeout(2)
    void largeHostCountsDoNotOverflowOrLoopForever() {
        assertThat(NetworkMath.cidrForHosts(Integer.MAX_VALUE)).isZero();
        assertThat(NetworkMath.cidrForHosts(1_073_741_824)).isEqualTo(1);
        assertThatThrownBy(() -> service.plan(new SubnetPlanRequest(
                "192.168.10.0/24", List.of(new DepartmentRequirement("Grande", Integer.MAX_VALUE))
        ))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no tiene espacio suficiente");
    }

    @Test
    void rejectsNonPositiveHostCountsAtTheDomainBoundary() {
        assertThatThrownBy(() -> NetworkMath.cidrForHosts(0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> NetworkMath.cidrForHosts(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

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
