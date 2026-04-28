package com.retechia.api.network;

public record SubnetAllocation(
        String department,
        String networkAddress,
        int cidr,
        long usableHosts,
        String firstUsableIp,
        String lastUsableIp,
        String broadcastAddress
) {
}
