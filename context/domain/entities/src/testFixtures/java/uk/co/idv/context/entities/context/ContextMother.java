package uk.co.idv.context.entities.context;

import uk.co.idv.context.entities.context.create.DefaultCreateContextRequestMother;
import uk.co.idv.context.entities.context.sequence.SequencesMother;

import java.time.Instant;
import java.util.UUID;

public interface ContextMother {

    static Context build() {
        return builder().build();
    }

    static Context.ContextBuilder builder() {
        return Context.builder()
                .id(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .created(Instant.parse("2020-09-14T20:02:02.002Z"))
                .request(DefaultCreateContextRequestMother.build())
                .sequences(SequencesMother.otpOnly());
    }

}
