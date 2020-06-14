package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.create.CreateIdentity;
import uk.co.idv.context.usecases.identity.merge.MergeIdentities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultUpdateIdentityTest {

    private final CreateIdentity create = mock(CreateIdentity.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);
    private final MergeIdentities merge = mock(MergeIdentities.class);
    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final UpdateIdentity externalUpdate = DefaultUpdateIdentity.builder()
            .create(create)
            .update(update)
            .merge(merge)
            .repository(repository)
            .build();

    @Test
    void shouldCreateIdentityIfNoExistingIdentities() {
        Identity identity = IdentityMother.example();
        givenNoExistingIdentities(identity.getAliases());

        externalUpdate.update(identity);

        verify(create).create(identity);
    }

    @Test
    void shouldUpdateIdentityIfOneExistingIdentity() {
        Identity updated = IdentityMother.example();
        givenOneExistingIdentity(updated.getAliases());

        externalUpdate.update(updated);

        verify(update).update(updated);
    }

    @Test
    void shouldMergeWithAllExistingIdentitiesIfMoreThanOneExistingIdentity() {
        Identity updated = IdentityMother.example();
        Collection<Identity> existing = givenMoreThanOneExistingIdentity(updated.getAliases());

        externalUpdate.update(updated);

        verify(merge).merge(updated, existing);
    }

    private void givenNoExistingIdentities(Aliases aliases) {
        given(repository.load(aliases)).willReturn(Collections.emptyList());
    }

    private void givenOneExistingIdentity(Aliases aliases) {
        given(repository.load(aliases)).willReturn(
                Collections.singleton(IdentityMother.example())
        );
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
