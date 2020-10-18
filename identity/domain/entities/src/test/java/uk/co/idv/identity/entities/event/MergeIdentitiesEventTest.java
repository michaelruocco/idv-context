package uk.co.idv.identity.entities.event;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.IdentitiesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;

class MergeIdentitiesEventTest {

    @Test
    void shouldReturnMergedIdentity() {
        Identity merged = IdentityMother.example();

        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .merged(merged)
                .build();

        assertThat(event.getMerged()).isEqualTo(merged);
    }

    @Test
    void shouldReturnIdvIdFromMergedIdentity() {
        Identity merged = IdentityMother.example();

        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .merged(merged)
                .build();

        assertThat(event.getMergedIdvId()).isEqualTo(merged.getIdvId());
    }

    @Test
    void shouldReturnOriginalIdentities() {
        Identities original = IdentitiesMother.two();

        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .original(original)
                .build();

        assertThat(event.getOriginal()).isEqualTo(original);
    }

    @Test
    void shouldReturnOriginalIdentityIdvIds() {
        Identity identity1 = IdentityMother.example();
        Identity identity2 = IdentityMother.example1();
        Identities original = IdentitiesMother.with(identity1, identity2);

        MergeIdentitiesEvent event = MergeIdentitiesEvent.builder()
                .original(original)
                .build();

        assertThat(event.getOriginalIdvIds()).containsExactly(
                identity1.getIdvId(),
                identity2.getIdvId()
        );
    }

}
