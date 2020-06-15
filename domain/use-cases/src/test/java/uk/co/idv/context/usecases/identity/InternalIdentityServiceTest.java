package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalIdentityServiceTest {

    private final UpdateIdentity update = mock(UpdateIdentity.class);
    private final FindIdentity find = mock(FindIdentity.class);

    private final IdentityService service = InternalIdentityService.builder()
            .update(update)
            .find(find)
            .build();

    @Test
    void shouldUpdateIdentity() {
        Identity expectedIdentity = IdentityMother.example();
        UpdateIdentityRequest request = UpdateIdentityRequestMother.build();
        given(update.update(request.getIdentity())).willReturn(expectedIdentity);

        Identity identity = service.update(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldFindIdentity() {
        Identity expectedIdentity = IdentityMother.example();
        FindIdentityRequest request = FindIdentityRequestMother.build();
        given(find.find(request)).willReturn(expectedIdentity);

        Identity identity = service.find(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
