package uk.co.idv.identity.usecases.eligibility.external.data;

import org.junit.jupiter.api.Test;

import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AsyncDataLoadRequestTest {

    @Test
    void shouldReturnTimeout() {
        Duration timeout = Duration.ofSeconds(5);

        AsyncDataLoadRequest request = AsyncDataLoadRequest.builder()
                .timeout(timeout)
                .build();

        assertThat(request.getTimeout()).isEqualTo(timeout);
    }

    @Test
    void shouldReturnAliases() {
        Aliases aliases = mock(Aliases.class);

        AsyncDataLoadRequest request = AsyncDataLoadRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnRequestedData() {
        RequestedData requestedData = mock(RequestedData.class);

        AsyncDataLoadRequest request = AsyncDataLoadRequest.builder()
                .requestedData(requestedData)
                .build();

        assertThat(request.getRequestedData()).isEqualTo(requestedData);
    }

    @Test
    void shouldReturnTrueIfEmailAddressesRequested() {
        RequestedData requestedData = mock(RequestedData.class);
        given(requestedData.emailAddressesRequested()).willReturn(true);

        AsyncDataLoadRequest request = AsyncDataLoadRequest.builder()
                .requestedData(requestedData)
                .build();

        assertThat(request.emailAddressesRequested()).isTrue();
    }

    @Test
    void shouldReturnTrueIfPhoneNumbersRequested() {
        RequestedData requestedData = mock(RequestedData.class);
        given(requestedData.phoneNumbersRequested()).willReturn(true);

        AsyncDataLoadRequest request = AsyncDataLoadRequest.builder()
                .requestedData(requestedData)
                .build();

        assertThat(request.phoneNumbersRequested()).isTrue();
    }

}
