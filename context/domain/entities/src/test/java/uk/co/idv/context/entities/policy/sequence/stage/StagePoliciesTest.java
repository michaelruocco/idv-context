package uk.co.idv.context.entities.policy.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StagePoliciesTest {

    @Test
    void shouldReturnRequestedDataFromAllMethods() {
        String data1 = "data-1";
        String data2 = "data-2";
        StagePolicies policies = new StagePolicies(
                givenStagePolicyWithRequestedData(data1),
                givenStagePolicyWithRequestedData(data2)
        );

        RequestedData allData = policies.getRequestedData();

        assertThat(allData).containsExactly(
                data1,
                data2
        );
    }

    @Test
    void shouldBeIterable() {
        StagePolicy policy1 = givenStagePolicy();
        StagePolicy policy2 = givenStagePolicy();

        StagePolicies policies = new StagePolicies(policy1, policy2);

        assertThat(policies).containsExactly(policy1, policy2);
    }

    @Test
    void shouldReturnStream() {
        StagePolicy policy1 = givenStagePolicy();
        StagePolicy policy2 = givenStagePolicy();
        StagePolicies policies = new StagePolicies(policy1, policy2);

        Stream<StagePolicy> stream = policies.stream();

        assertThat(stream).containsExactly(policy1, policy2);
    }

    private StagePolicy givenStagePolicyWithRequestedData(String... items) {
        StagePolicy policy = givenStagePolicy();
        given(policy.getRequestedData()).willReturn(RequestedDataMother.with(items));
        return policy;
    }

    private StagePolicy givenStagePolicy() {
        return mock(StagePolicy.class);
    }

}
