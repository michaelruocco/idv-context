package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentitiesTest {

    @Test
    void shouldBeIterable() {
        Identities identities = IdentitiesMother.two();

        assertThat(identities).containsExactly(
                IdentityMother.example(),
                IdentityMother.example1()
        );
    }

    @Test
    void shouldReturnFirstIdentity() {
        Identities identities = IdentitiesMother.two();

        Identity identity = identities.getFirst();

        assertThat(identity).isEqualTo(IdentityMother.example());
    }

    @Test
    void shouldReturnSizeZeroIfEmpty() {
        Identities identities = IdentitiesMother.empty();

        int size = identities.size();

        assertThat(size).isZero();
    }

    @Test
    void shouldReturnSizeIfPopulated() {
        Identities identities = IdentitiesMother.two();

        int size = identities.size();

        assertThat(size).isEqualTo(2);
    }

    @Test
    void shouldReturnIdvIdsOfIdentities() {
        Identities identities = IdentitiesMother.two();

        Aliases aliases = identities.getIdvIds();

        assertThat(aliases).containsExactly(
                IdvIdMother.idvId(),
                IdvIdMother.idvId1()
        );
    }

}
