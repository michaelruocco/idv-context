package uk.co.idv.lockout.entities.attempt;

import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.attempt.Attempt.AttemptBuilder;

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
        return withAliases(AliasesMother.with(alias));
    }

    static Attempt withAliases(DefaultAliases aliases) {
        return builder().aliases(aliases).build();
    }

    static Attempt withChannelId(String channelId) {
        return builder().channelId(channelId).build();
    }

    static Attempt successful() {
        return builder().successful(true).build();
    }

    static Attempt unsuccessful(Instant timestamp) {
        return builder()
                .timestamp(timestamp)
                .successful(false)
                .build();
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
                .aliases(AliasesMother.creditCardNumberOnly())
                .idvId(IdvIdMother.idvId())
                .methodName("default-method")
                .verificationId(UUID.fromString("1fb7cd98-694d-4ba4-968a-9b86bbf52c01"))
                .timestamp(Instant.parse("2019-09-27T09:35:15.612Z"))
                .successful(true);
    }

}
