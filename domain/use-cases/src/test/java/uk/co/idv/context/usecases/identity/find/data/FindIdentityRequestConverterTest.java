package uk.co.idv.context.usecases.identity.find.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class FindIdentityRequestConverterTest {

    private final TimeoutProvider timeoutProvider = mock(TimeoutProvider.class);

    private final FindIdentityRequestConverter converter = new FindIdentityRequestConverter(timeoutProvider);

    @Test
    void shouldPopulateTimeoutOnAsyncDataLoadRequest() {
        ExternalFindIdentityRequest findRequest = ExternalFindIdentityRequestMother.build();
        Duration timeout = Duration.ofSeconds(1);
        given(timeoutProvider.getTimeout(findRequest.getChannelId())).willReturn(timeout);
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        AsyncDataLoadRequest loadRequest = converter.toAsyncDataLoadRequest(aliases, findRequest);

        assertThat(loadRequest.getTimeout()).isEqualTo(timeout);
    }

    @Test
    void shouldPopulateAliasesOnAsyncDataLoadRequest() {
        ExternalFindIdentityRequest findRequest = ExternalFindIdentityRequestMother.build();
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        AsyncDataLoadRequest loadRequest = converter.toAsyncDataLoadRequest(aliases, findRequest);

        assertThat(loadRequest.getAliases()).isEqualTo(aliases);
    }

}
