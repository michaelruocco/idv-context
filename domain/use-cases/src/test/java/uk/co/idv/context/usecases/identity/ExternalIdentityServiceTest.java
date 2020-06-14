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

    private final UpsertIdentityRequestConverter converter = mock(UpsertIdentityRequestConverter.class);
    private final FindIdentity find = mock(FindIdentity.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final IdentityService service = ExternalIdentityService.builder()
            .converter(converter)
            .find(find)
            .update(update)
            .build();

    @Test
    void shouldFindExternalIdentityAndThenReturnAfterUpdatingAgainstExistingIdentities() {
        Identity foundIdentity = IdentityMother.example();
        UpsertIdentityRequest upsertRequest = UpsertIdentityRequestMother.build();
        FindIdentityRequest findRequest = FindIdentityRequestMother.build();
        given(converter.toFindRequest(upsertRequest)).willReturn(findRequest);
        given(find.find(findRequest)).willReturn(foundIdentity);
        Identity updatedIdentity = IdentityMother.example1();
        given(update.update(foundIdentity)).willReturn(updatedIdentity);

        Identity identity = service.upsert(upsertRequest);

        assertThat(identity).isEqualTo(updatedIdentity);
    }

}
