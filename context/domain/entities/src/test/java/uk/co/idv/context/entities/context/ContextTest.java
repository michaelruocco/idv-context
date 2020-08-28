package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequestMother;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        Context context = Context.builder()
                .id(id)
                .build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnCreated() {
        Instant created = Instant.now();

        Context context = Context.builder()
                .created(created)
                .build();

        assertThat(context.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnIdentityCreateContextRequest() {
        IdentityCreateContextRequest request = IdentityCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getRequest()).isEqualTo(request);
    }

}
