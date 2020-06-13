package uk.co.idv.context.usecases.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalFindIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);


    private final FindIdentity loader = new InternalFindIdentity(repository);

    @Test
    void shouldReturnIdentityIfIdentityExists() {
        Identity expectedIdentity = IdentityMother.build();
        FindIdentityRequest request = givenRequestWithAlias(expectedIdentity.getIdvId());
        givenIdentityExists(expectedIdentity);

        Identity identity = loader.find(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExist() {
        Alias alias = IdvIdMother.idvId();
        FindIdentityRequest request = givenRequestWithAlias(alias);

        IdentityNotFoundException error = catchThrowableOfType(
                () -> loader.find(request),
                IdentityNotFoundException.class
        );

        assertThat(error.getAlias()).isEqualTo(alias);
    }

    private void givenIdentityExists(Identity identity) {
        given(repository.load(identity.getIdvId())).willReturn(Optional.of(identity));
    }

    private FindIdentityRequest givenRequestWithAlias(Alias alias) {
        FindIdentityRequest request = mock(FindIdentityRequest.class);
        given(request.getProvidedAlias()).willReturn(alias);
        return request;
    }

}
