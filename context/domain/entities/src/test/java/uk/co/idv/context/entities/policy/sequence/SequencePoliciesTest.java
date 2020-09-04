package uk.co.idv.context.entities.policy.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencePoliciesTest {

    @Test
    void shouldReturnRequestedDataFromAllSequences() {
        String data1 = "data-1";
        String data2 = "data-2";
        SequencePolicies policies = new SequencePolicies(
                givenMethodSequenceWithRequestedData(data1),
                givenMethodSequenceWithRequestedData(data2)
        );

        RequestedData allData = policies.getRequestedData();

        assertThat(allData).containsExactly(
                data1,
                data2
        );
    }

    private SequencePolicy givenMethodSequenceWithRequestedData(String... items) {
        SequencePolicy policy = mock(SequencePolicy.class);
        given(policy.getRequestedData()).willReturn(RequestedDataMother.with(items));
        return policy;
    }

}
