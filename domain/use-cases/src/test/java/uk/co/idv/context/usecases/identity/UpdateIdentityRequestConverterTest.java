package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateIdentityRequestConverterTest {

    private final UpsertIdentityRequestConverter converter = new UpsertIdentityRequestConverter();

    @Test
    void shouldPopulateAliasesOnFindRequest() {
        UpdateIdentityRequest upsertRequest = UpdateIdentityRequestMother.build();

        FindIdentityRequest findRequest = converter.toFindRequest(upsertRequest);

        assertThat(findRequest.getAliases()).isEqualTo(upsertRequest.getAliases());
    }

    @Test
    void shouldPopulateChannelOnFindRequest() {
        UpdateIdentityRequest upsertRequest = UpdateIdentityRequestMother.build();

        FindIdentityRequest findRequest = converter.toFindRequest(upsertRequest);

        assertThat(findRequest.getChannel()).isEqualTo(upsertRequest.getChannel());
    }

}
