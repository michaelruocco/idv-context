package uk.co.idv.context.entities.policy.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencePoliciesTest {

    @Test
    void shouldReturnRequestedDataFromAllSequences() {
        String data1 = "data-1";
        String data2 = "data-2";
        SequencePolicies policies = new SequencePolicies(
                givenSequencePolicyWithRequestedData(data1),
                givenSequencePolicyWithRequestedData(data2)
        );

        RequestedData allData = policies.getRequestedData();

        assertThat(allData).containsExactly(
                data1,
                data2
        );
    }

    @Test
    void shouldReturnStreamOfSequencePolicies() {
        SequencePolicy policy1 = givenSequencePolicy();
        SequencePolicy policy2 = givenSequencePolicy();
        SequencePolicies policies = new SequencePolicies(policy1, policy2);

        Stream<SequencePolicy> stream = policies.stream();

        assertThat(stream).containsExactly(policy1, policy2);
    }

    private SequencePolicy givenSequencePolicyWithRequestedData(String... items) {
        SequencePolicy policy = givenSequencePolicy();
        given(policy.getRequestedData()).willReturn(RequestedDataMother.with(items));
        return policy;
    }

    private SequencePolicy givenSequencePolicy() {
        return mock(SequencePolicy.class);
    }

}
