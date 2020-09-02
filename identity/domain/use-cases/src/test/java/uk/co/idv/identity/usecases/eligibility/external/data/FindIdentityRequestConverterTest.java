package uk.co.idv.identity.usecases.eligibility.external.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class FindIdentityRequestConverterTest {

    private final TimeoutProvider timeoutProvider = mock(TimeoutProvider.class);

    private final FindIdentityRequestConverter converter = new FindIdentityRequestConverter(timeoutProvider);

    @Test
    void shouldPopulateTimeoutOnAsyncDataLoadRequest() {
        FindIdentityRequest findRequest = CreateEligibilityRequestMother.build();
        Duration timeout = Duration.ofSeconds(1);
        given(timeoutProvider.getTimeout(findRequest.getChannelId())).willReturn(timeout);
        DefaultAliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        AsyncDataLoadRequest loadRequest = converter.toAsyncDataLoadRequest(aliases, findRequest);

        assertThat(loadRequest.getTimeout()).isEqualTo(timeout);
    }

    @Test
    void shouldPopulateAliasesOnAsyncDataLoadRequest() {
        FindIdentityRequest findRequest = CreateEligibilityRequestMother.build();
        DefaultAliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        AsyncDataLoadRequest loadRequest = converter.toAsyncDataLoadRequest(aliases, findRequest);

        assertThat(loadRequest.getAliases()).isEqualTo(aliases);
    }

}
