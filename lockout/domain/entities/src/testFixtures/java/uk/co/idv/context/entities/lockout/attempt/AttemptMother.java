package uk.co.idv.context.entities.lockout.attempt;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempt.AttemptBuilder;

import java.time.Instant;
import java.util.UUID;

public interface AttemptMother {

    static Attempt withIdvId(IdvId idvId) {
        return builder().idvId(idvId).build();
    }

    static Attempt withTimestamp(Instant timestamp) {
        return builder().timestamp(timestamp).build();
    }

    static Attempt withAlias(Alias alias) {
        return builder().alias(alias).build();
    }

    static Attempt withChannelId(String channelId) {
        return builder().channelId(channelId).build();
    }

    static Attempt successful() {
        return builder().successful(true).build();
    }

    static Attempt unsuccessful() {
        return builder().successful(false).build();
    }

    static Attempt build() {
        return builder().build();
    }

    static AttemptBuilder builder() {
        return Attempt.builder()
                .contextId(UUID.fromString("fb059cfd-5613-49fe-8f34-2264b5da8343"))
                .channelId("default-channel")
                .activityName("default-activity")
                .alias(CreditCardNumberMother.creditCardNumber())
                .idvId(IdvIdMother.idvId())
                .methodName("default-method")
                .verificationId(UUID.fromString("1fb7cd98-694d-4ba4-968a-9b86bbf52c01"))
                .timestamp(Instant.parse("2019-09-27T09:35:15.612Z"))
                .successful(true);
    }

}
