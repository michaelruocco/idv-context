package uk.co.idv.context.entities.context;

import uk.co.idv.context.entities.context.sequence.SequencesMother;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.method.entities.verification.VerificationsMother;

import java.time.Instant;
import java.util.UUID;

public interface ApiContextMother {

    static ApiContext build() {
        return builder().build();
    }

    static ApiContext.ApiContextBuilder builder() {
        return ApiContext.builder()
                .id(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .created(Instant.parse("2020-09-14T20:02:02.002Z"))
                .expiry(Instant.parse("2020-09-14T20:08:02.002Z"))
                .channel(DefaultChannelMother.withData())
                .verifications(VerificationsMother.oneIncomplete())
                .sequences(SequencesMother.fakeOnly());
    }

}
