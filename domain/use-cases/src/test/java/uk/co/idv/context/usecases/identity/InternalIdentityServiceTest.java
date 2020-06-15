package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.service.find.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.request.FindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;
import uk.co.idv.context.usecases.identity.service.InternalIdentityService;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalIdentityServiceTest {

    private final UpdateIdentity update = mock(UpdateIdentity.class);
    private final InternalFindIdentity find = mock(InternalFindIdentity.class);

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
