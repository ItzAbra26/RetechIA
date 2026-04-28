package com.retechia.api.network;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SubnetPlannerService {

    public SubnetPlanResponse plan(SubnetPlanRequest request) {
        BaseNetwork baseNetwork = parseBaseNetwork(request.baseNetwork());
        List<DepartmentRequirement> departments = request.departments().stream()
                .sorted(Comparator.comparingInt(DepartmentRequirement::hosts).reversed())
                .toList();

        long currentNetworkAddress = baseNetwork.networkAddress();
        List<SubnetAllocation> allocations = new java.util.ArrayList<>();

        for (DepartmentRequirement department : departments) {
            SubnetAllocation allocation = allocate(department, baseNetwork, currentNetworkAddress);
            allocations.add(allocation);
            currentNetworkAddress += NetworkMath.blockSizeFromCidr(allocation.cidr());
        }

        return new SubnetPlanResponse(request.baseNetwork(), allocations);
    }

    private SubnetAllocation allocate(DepartmentRequirement department, BaseNetwork baseNetwork, long currentAddress) {
        int cidr = NetworkMath.cidrForHosts(department.hosts());
        long blockSize = NetworkMath.blockSizeFromCidr(cidr);
        long broadcast = currentAddress + blockSize - 1;

        if (broadcast > baseNetwork.broadcastAddress()) {
            throw new IllegalArgumentException("La red base no tiene espacio suficiente para " + department.name() + ".");
        }

        return new SubnetAllocation(
                department.name(),
                NetworkMath.longToIp(currentAddress),
                cidr,
                Math.max(blockSize - 2, 0),
                NetworkMath.longToIp(currentAddress + 1),
                NetworkMath.longToIp(broadcast - 1),
                NetworkMath.longToIp(broadcast)
        );
    }

    private BaseNetwork parseBaseNetwork(String value) {
        String[] parts = value.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("La red base debe tener formato 192.168.1.0/24.");
        }

        int cidr = Integer.parseInt(parts[1]);
        long ip = NetworkMath.ipToLong(parts[0]);
        long mask = NetworkMath.maskFromCidr(cidr);
        long networkAddress = ip & mask;
        long totalAddresses = NetworkMath.blockSizeFromCidr(cidr);
        long broadcastAddress = networkAddress + totalAddresses - 1;

        return new BaseNetwork(networkAddress, broadcastAddress, totalAddresses);
    }

    private record BaseNetwork(long networkAddress, long broadcastAddress, long totalAddresses) {
    }
}
