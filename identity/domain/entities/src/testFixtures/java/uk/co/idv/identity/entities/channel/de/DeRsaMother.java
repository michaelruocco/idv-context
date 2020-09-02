package uk.co.idv.identity.entities.channel.de;

import java.util.UUID;

public interface DeRsaMother {

    static DeRsa withoutIssuerSessionId() {
        return withIssuerSessionId(null);
    }

    static DeRsa withIssuerSessionId(UUID id) {
        return builder().issuerSessionId(id).build();
    }

    static DeRsa withoutDsSessionId() {
        return withDsSessionId(null);
    }

    static DeRsa withDsSessionId(UUID id) {
        return builder().dsSessionId(id).build();
    }

    static DeRsa rsa() {
        return builder().build();
    }

    static DeRsa.DeRsaBuilder builder() {
        return DeRsa.builder()
                .issuerSessionId(UUID.fromString("6d1d7fdb-7f78-4e04-a19a-2444950773fa"))
                .dsSessionId(UUID.fromString("24974f59-c62d-4505-8c42-595ce9e5ad99"));
    }

}
