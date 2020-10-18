package uk.co.idv.identity.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.IdentitiesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.create.CreateIdentity;
import uk.co.idv.identity.usecases.identity.merge.MergeIdentities;
import uk.co.idv.identity.usecases.identity.save.SaveIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UpdateIdentityTest {

    private final CreateIdentity create = mock(CreateIdentity.class);
    private final SaveIdentity save = mock(SaveIdentity.class);
    private final MergeIdentities merge = mock(MergeIdentities.class);
    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final UpdateIdentity update = UpdateIdentity.builder()
            .create(create)
            .save(save)
            .merge(merge)
            .repository(repository)
            .build();

    @Test
    void shouldCreateIdentityIfNoExistingIdentities() {
        Identity identity = IdentityMother.example();
        givenNoExistingIdentities(identity.getAliases());
        Identity expected = IdentityMother.example1();
        given(create.create(identity)).willReturn(expected);

        Identity created = update.update(identity);

        assertThat(created).isEqualTo(expected);
    }

    @Test
    void shouldUpdateIdentityIfOneExistingIdentity() {
        Identity identity = IdentityMother.example();
        Identity existing = givenOneExistingIdentity(identity.getAliases());
        Identity saved = IdentityMother.example1();
        given(save.save(identity, existing)).willReturn(saved);

        Identity updated = update.update(identity);

        assertThat(updated).isEqualTo(saved);
    }

    @Test
    void shouldMergeWithAllExistingIdentitiesIfMoreThanOneExistingIdentity() {
        Identity identity = IdentityMother.example();
        Identities existing = givenMoreThanOneExistingIdentity(identity.getAliases());
        Identity expected = IdentityMother.example1();
        given(merge.merge(identity, existing)).willReturn(expected);

        Identity merged = update.update(identity);

        assertThat(merged).isEqualTo(expected);
    }

    private void givenNoExistingIdentities(Aliases aliases) {
        given(repository.load(aliases)).willReturn(IdentitiesMother.empty());
    }

    private Identity givenOneExistingIdentity(Aliases aliases) {
        Identity existing = IdentityMother.example();
        given(repository.load(aliases)).willReturn(IdentitiesMother.with(existing));
        return existing;
    }

    private Identities givenMoreThanOneExistingIdentity(Aliases aliases) {
        Identities existing = IdentitiesMother.two();
        given(repository.load(aliases)).willReturn(existing);
        return existing;
    }

}
