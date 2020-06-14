package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalIdentityServiceTest {

    private final FindIdentity find = mock(FindIdentity.class);

    private final IdentityService service = InternalIdentityService.builder()
            .find(find)
            .build();

    @Test
    void shouldFindIdentity() {
        Identity expectedIdentity = IdentityMother.example();
        FindIdentityRequest request = FindIdentityRequestMother.build();
        given(find.find(request)).willReturn(expectedIdentity);

        Identity identity = service.find(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
