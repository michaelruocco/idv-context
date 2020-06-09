package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultIdentityLoaderTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);


    private final IdentityLoader loader = new DefaultIdentityLoader(repository);

    @Test
    void shouldLoadIdentityIfIdentityExists() {
        Identity expectedIdentity = IdentityMother.build();
        LoadIdentityRequest request = givenRequestWithAlias(expectedIdentity.getIdvId());
        givenIdentityExists(expectedIdentity);

        Identity identity = loader.load(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesNotExist() {
        Alias alias = IdvIdMother.idvId();
        LoadIdentityRequest request = givenRequestWithAlias(alias);

        IdentityNotFoundException error = catchThrowableOfType(
                () -> loader.load(request),
                IdentityNotFoundException.class
        );

        assertThat(error.getAlias()).isEqualTo(alias);
    }

    private void givenIdentityExists(Identity identity) {
        given(repository.load(identity.getIdvId())).willReturn(Optional.of(identity));
    }

    private LoadIdentityRequest givenRequestWithAlias(Alias alias) {
        LoadIdentityRequest request = mock(LoadIdentityRequest.class);
        given(request.getAlias()).willReturn(alias);
        return request;
    }

}
