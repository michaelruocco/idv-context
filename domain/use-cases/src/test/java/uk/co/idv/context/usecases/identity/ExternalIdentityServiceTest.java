package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalIdentityServiceTest {

    private final FindIdentity find = mock(FindIdentity.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final IdentityService service = ExternalIdentityService.builder()
            .find(find)
            .update(update)
            .build();

    @Test
    void shouldReturnIdentityFoundAfterUpdatingAgainstExistingIdentities() {
        Identity foundIdentity = IdentityMother.example();
        FindIdentityRequest request = FindIdentityRequestMother.build();
        given(find.find(request)).willReturn(foundIdentity);
        Identity updatedIdentity = IdentityMother.example1();
        given(update.update(foundIdentity)).willReturn(updatedIdentity);

        Identity identity = service.find(request);

        assertThat(identity).isEqualTo(updatedIdentity);
    }

}
