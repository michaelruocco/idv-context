package uk.co.idv.identity.entities.event;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MergeIdentitiesEventTest {

    @Test
    void shouldReturnMergedIdentity() {
        Identity merged = mock(Identity.class);

        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .merged(merged)
                .build();

        assertThat(event.getMerged()).isEqualTo(merged);
    }

    @Test
    void shouldReturnOriginalIdentities() {
        Identities original = mock(Identities.class);

        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .original(original)
                .build();

        assertThat(event.getOriginal()).isEqualTo(original);
    }

}
