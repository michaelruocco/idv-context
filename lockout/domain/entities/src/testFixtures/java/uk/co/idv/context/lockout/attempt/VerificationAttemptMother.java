package uk.co.idv.context.lockout.attempt;

import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.lockout.attempt.VerificationAttempt.VerificationAttemptBuilder;

import java.time.Instant;
import java.util.UUID;

public class VerificationAttemptMother {

    private VerificationAttemptMother() {
        // utility class
    }

    public static VerificationAttempt build() {
        return builder().build();
    }

    public static VerificationAttempt withIdvId(IdvId idvId) {
        return builder().idvId(idvId).build();
    }

    public static VerificationAttemptBuilder builder() {
        return VerificationAttempt.builder()
                .contextId(UUID.fromString("fb059cfd-5613-49fe-8f34-2264b5da8343"))
                .channelId("fake-channel")
                .activityName("fake-activity")
                .alias(CreditCardNumberMother.creditCardNumber())
                .idvId(IdvIdMother.idvId())
                .methodName("fake-method")
                .verificationId(UUID.fromString("1fb7cd98-694d-4ba4-968a-9b86bbf52c01"))
                .timestamp(Instant.parse("2019-09-27T09:35:15.612Z"))
                .successful(true);
    }

}
