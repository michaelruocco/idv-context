package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InternalIdentityServiceTest {

    private final FindIdentity find = mock(FindIdentity.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final IdentityService service = InternalIdentityService.builder()
            .find(find)
            .update(update)
            .build();

    @Test
    void shouldFindIdentity() {
        Identity expectedIdentity = IdentityMother.example();
        FindIdentityRequest request = FindIdentityRequestMother.build();
        given(find.find(request)).willReturn(expectedIdentity);

        Identity identity = service.find(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldUpdateIdentity() {
        Identity identity = IdentityMother.example();

        service.update(identity);

        verify(update).update(identity);
    }

}
