package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;
import uk.co.idv.context.usecases.identity.service.UpdateIdentityRequestConverter;
import uk.co.idv.context.usecases.identity.service.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentityRequest;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateIdentityRequestConverterTest {

    private final UpdateIdentityRequestConverter converter = new UpdateIdentityRequestConverter();

    @Test
    void shouldPopulateAliasesOnFindRequest() {
        UpdateIdentityRequest updateRequest = UpdateIdentityRequestMother.build();

        FindIdentityRequest findRequest = converter.toFindRequest(updateRequest);

        assertThat(findRequest.getAliases()).isEqualTo(updateRequest.getAliases());
    }

    @Test
    void shouldPopulateChannelOnFindRequest() {
        UpdateIdentityRequest updateRequest = UpdateIdentityRequestMother.build();

        ExternalFindIdentityRequest findRequest = converter.toFindRequest(updateRequest);

        assertThat(findRequest.getChannel()).isEqualTo(updateRequest.getChannel());
    }

}
