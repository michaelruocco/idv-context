package uk.co.idv.context.entities.context;

import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequenceMother;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesMother;
import uk.co.idv.context.entities.verification.Verifications;
import uk.co.idv.context.entities.verification.VerificationsMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.Method;

import java.time.Instant;
import java.util.UUID;

public interface ContextMother {

    static Context build() {
        return builder().build();
    }

    static Context withId(UUID id) {
        return builder().id(id).build();
    }

    static Context withExpiry(Instant expiry) {
        return builder().expiry(expiry).build();
    }

    static Context withSequences(Sequences sequences) {
        return builder().sequences(sequences).build();
    }

    static Context withVerifications(Verifications verifications) {
        return builder().verifications(verifications).build();
    }

    static Context withIdentity(Identity identity) {
        return builder().request(ServiceCreateContextRequestMother.withIdentity(identity)).build();
    }

    static Context withMethod(Method method) {
        Sequence sequence = SequenceMother.withMethods(method);
        return ContextMother.withSequences(SequencesMother.with(sequence));
    }

    static Context.ContextBuilder builder() {
        return Context.builder()
                .id(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .created(Instant.parse("2020-09-14T20:02:02.002Z"))
                .expiry(Instant.parse("2020-09-14T20:08:02.002Z"))
                .request(ServiceCreateContextRequestMother.build())
                .verifications(VerificationsMother.oneIncomplete())
                .sequences(SequencesMother.fakeOnly());
    }

}
