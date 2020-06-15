package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.service.find.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.service.find.InternalFindIdentity;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.request.FindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;
import uk.co.idv.context.usecases.identity.service.ExternalIdentityService;
import uk.co.idv.context.usecases.identity.service.UpdateIdentityRequestConverter;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalIdentityServiceTest {

    private final UpdateIdentityRequestConverter converter = mock(UpdateIdentityRequestConverter.class);
    private final ExternalFindIdentity externalFind = mock(ExternalFindIdentity.class);
    private final InternalFindIdentity internalFind = mock(InternalFindIdentity.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final IdentityService service = ExternalIdentityService.builder()
            .converter(converter)
            .externalFind(externalFind)
            .internalFind(internalFind)
            .update(update)
            .build();

    @Test
    void shouldFindExternalIdentityAndThenReturnAfterUpdatingAgainstExistingIdentities() {
        Identity foundIdentity = IdentityMother.example();
        UpdateIdentityRequest updateRequest = UpdateIdentityRequestMother.build();
        ExternalFindIdentityRequest findRequest = ExternalFindIdentityRequestMother.build();
        given(converter.toFindRequest(updateRequest)).willReturn(findRequest);
        given(externalFind.find(findRequest)).willReturn(foundIdentity);
        Identity updatedIdentity = IdentityMother.example1();
        given(update.update(foundIdentity)).willReturn(updatedIdentity);

        Identity identity = service.update(updateRequest);

        assertThat(identity).isEqualTo(updatedIdentity);
    }

    @Test
    void shouldFindInternalIdentity() {
        Identity expectedIdentity = IdentityMother.example();
        FindIdentityRequest request = FindIdentityRequestMother.build();
        given(internalFind.find(request)).willReturn(expectedIdentity);

        Identity identity = service.find(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
