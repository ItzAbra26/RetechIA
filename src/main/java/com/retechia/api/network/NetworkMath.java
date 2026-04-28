package com.retechia.api.network;

final class NetworkMath {

    private NetworkMath() {
    }

    static long ipToLong(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("La IP debe tener formato IPv4.");
        }

        long value = 0;
        for (String part : parts) {
            int octet = Integer.parseInt(part);
            if (octet < 0 || octet > 255) {
                throw new IllegalArgumentException("Cada octeto debe estar entre 0 y 255.");
            }
            value = (value << 8) | octet;
        }
        return value;
    }

    static String longToIp(long value) {
        return String.format(
                "%d.%d.%d.%d",
                (value >> 24) & 255,
                (value >> 16) & 255,
                (value >> 8) & 255,
                value & 255
        );
    }

    static long maskFromCidr(int cidr) {
        if (cidr < 0 || cidr > 32) {
            throw new IllegalArgumentException("El CIDR debe estar entre 0 y 32.");
        }
        return cidr == 0 ? 0 : (0xffffffffL << (32 - cidr)) & 0xffffffffL;
    }

    static long blockSizeFromCidr(int cidr) {
        return 1L << (32 - cidr);
    }

    static int cidrForHosts(int hosts) {
        int needed = hosts + 2;
        int bits = 0;
        int capacity = 1;

        while (capacity < needed) {
            capacity *= 2;
            bits++;
        }

        return 32 - bits;
    }
}
