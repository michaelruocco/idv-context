package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RequestedDataMergerTest {

    @Test
    void shouldMergeRequestedDataFromAllProviders() {
        RequestedDataProvider provider1 = givenProviderWithData("data-1");
        RequestedDataProvider provider2 = givenProviderWithData("data-2");

        RequestedData merged = RequestedDataMerger.mergeRequestedData(provider1, provider2);

        assertThat(merged).containsExactly("data-1", "data-2");
    }

    private RequestedDataProvider givenProviderWithData(String... items) {
        RequestedDataProvider provider = mock(RequestedDataProvider.class);
        given(provider.getRequestedData()).willReturn(RequestedDataMother.with(items));
        return provider;
    }

}
