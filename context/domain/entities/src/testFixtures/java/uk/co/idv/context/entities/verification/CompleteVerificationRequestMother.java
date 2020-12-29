package uk.co.idv.context.entities.verification;


import java.time.Instant;
import java.util.UUID;

public interface CompleteVerificationRequestMother {

    static CompleteVerificationRequest successful() {
        return builder().build();
    }

    static CompleteVerificationRequest.CompleteVerificationRequestBuilder builder() {
        return CompleteVerificationRequest.builder()
                .verificationId(UUID.fromString("81e11840-140e-45ac-a6af-40aa653a146e"))
                .successful(true)
                .timestamp(Instant.parse("2020-09-14T20:05:05.005Z"));
    }

}
