package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.merge.MergeIdentities;
import uk.co.idv.context.usecases.identity.save.SaveIdentity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
        Collection<Identity> existing = givenMoreThanOneExistingIdentity(identity.getAliases());
        Identity expected = IdentityMother.example1();
        given(merge.merge(identity, existing)).willReturn(expected);

        Identity merged = update.update(identity);

        assertThat(merged).isEqualTo(expected);
    }

    private void givenNoExistingIdentities(Aliases aliases) {
        given(repository.load(aliases)).willReturn(Collections.emptyList());
    }

    private Identity givenOneExistingIdentity(Aliases aliases) {
        Identity identity = IdentityMother.example();
        given(repository.load(aliases)).willReturn(
                Collections.singleton(identity)
        );
        return identity;
    }

    private Collection<Identity> givenMoreThanOneExistingIdentity(Aliases aliases) {
        Collection<Identity> existing = Arrays.asList(
                IdentityMother.example(),
                IdentityMother.example()
        );
        given(repository.load(aliases)).willReturn(
                existing
        );
        return existing;
    }

}
