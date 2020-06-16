package uk.co.idv.context.usecases.identity.update.external;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.UpdateIdentityRequestConverter;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentityRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExternalUpdateIdentityTest {

    private final UpdateIdentityRequestConverter converter = mock(UpdateIdentityRequestConverter.class);
    private final ExternalFindIdentity externalFind = mock(ExternalFindIdentity.class);
    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final ExternalUpdateIdentity externalUpdate = ExternalUpdateIdentity.builder()
            .converter(converter)
            .externalFind(externalFind)
            .update(update)
            .build();

    @Test
    void shouldUpdateRequestWithExternalIdentityWhenPerformingUpdate() {
        UpdateIdentityRequest updateRequest = UpdateIdentityRequestMother.build();
        ExternalFindIdentityRequest findRequest = ExternalFindIdentityRequestMother.build();
        given(converter.toFindRequest(updateRequest)).willReturn(findRequest);
        Identity expectedIdentity = IdentityMother.example();
        given(externalFind.find(findRequest)).willReturn(expectedIdentity);
        ArgumentCaptor<UpdateIdentityRequest> captor = ArgumentCaptor.forClass(UpdateIdentityRequest.class);

        externalUpdate.update(updateRequest);

        verify(update).update(captor.capture());
        UpdateIdentityRequest actualUpdateRequest = captor.getValue();
        assertThat(actualUpdateRequest).isEqualToIgnoringGivenFields(updateRequest, "identity");
        assertThat(actualUpdateRequest.getIdentity()).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldReturnIdentityFromResultOfUpdate() {
        UpdateIdentityRequest updateRequest = UpdateIdentityRequestMother.build();
        Identity expectedIdentity = IdentityMother.example();
        given(update.update(any(UpdateIdentityRequest.class))).willReturn(expectedIdentity);

        Identity identity = externalUpdate.update(updateRequest);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
