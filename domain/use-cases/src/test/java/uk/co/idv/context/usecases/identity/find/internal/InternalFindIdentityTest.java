package uk.co.idv.context.usecases.identity.find.internal;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.merge.MultipleIdentitiesFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalFindIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final InternalFindIdentity loader = new InternalFindIdentity(repository);

    @Test
    void shouldThrowExceptionIfNoIdentitiesExist() {
        FindIdentityRequest request = ExternalFindIdentityRequestMother.build();
        givenNoExistingIdentities(request.getAliases());

        IdentityNotFoundException error = catchThrowableOfType(
                () -> loader.find(request),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldReturnIdentityIfOneExistingIdentity() {
        FindIdentityRequest request = ExternalFindIdentityRequestMother.build();
        Identity expected = IdentityMother.example();
        givenOneExistingIdentity(request.getAliases(), expected);

        Identity identity = loader.find(request);

        assertThat(identity).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionIfMoreThanOneIdentityExists() {
        FindIdentityRequest request = ExternalFindIdentityRequestMother.build();
        Collection<Identity> existing = givenMoreThanOneExistingIdentity(request.getAliases());

        MultipleIdentitiesFoundException error = catchThrowableOfType(
                () -> loader.find(request),
                MultipleIdentitiesFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(request.getAliases());
        assertThat(error.getExistingIdentities()).isEqualTo(existing);
    }

    private void givenNoExistingIdentities(Aliases aliases) {
        given(repository.load(aliases)).willReturn(Collections.emptyList());
    }

    private void givenOneExistingIdentity(Aliases aliases, Identity identity) {
        given(repository.load(aliases)).willReturn(
                Collections.singleton(identity)
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
