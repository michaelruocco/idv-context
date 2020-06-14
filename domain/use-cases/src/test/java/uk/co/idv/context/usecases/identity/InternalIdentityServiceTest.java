package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class InternalIdentityServiceTest {

    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final IdentityService service = InternalIdentityService.builder()
            .update(update)
            .build();

    @Test
    void shouldFindIdentity() {
        Identity expectedIdentity = IdentityMother.example();
        UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        given(update.update(request.getIdentity())).willReturn(expectedIdentity);

        Identity identity = service.upsert(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
