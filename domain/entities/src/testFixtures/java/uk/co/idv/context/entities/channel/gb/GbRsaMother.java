package uk.co.idv.context.entities.channel.gb;

import java.util.UUID;

public interface GbRsaMother {

    static GbRsa withoutIssuerSessionId() {
        return withIssuerSessionId(null);
    }

    static GbRsa withIssuerSessionId(UUID id) {
        return builder().issuerSessionId(id).build();
    }

    static GbRsa rsa() {
        return builder().build();
    }

    static GbRsa.GbRsaBuilder builder() {
        return GbRsa.builder()
                .issuerSessionId(UUID.fromString("053ed9b4-74a6-4d13-a7d5-3970e59a7aad"));
    }

}
