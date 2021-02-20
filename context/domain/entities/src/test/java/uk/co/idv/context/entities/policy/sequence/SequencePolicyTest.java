package uk.co.idv.context.entities.policy.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencePolicyTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        SequencePolicy policy = SequencePolicy.builder()
                .name(name)
                .build();

        assertThat(policy.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnStagePolicies() {
        StagePolicies stagePolicies = givenStagePolicies();

        SequencePolicy policy = SequencePolicy.builder()
                .stagePolicies(stagePolicies)
                .build();

        assertThat(policy.getStagePolicies()).isEqualTo(stagePolicies);
    }

    @Test
    void shouldReturnRequestedDataFromStagePolicies() {
        RequestedData requestedData = RequestedDataMother.allRequested();

        SequencePolicy policy = SequencePolicy.builder()
                .stagePolicies(givenStagePoliciesWithRequestedData(requestedData))
                .build();

        assertThat(policy.getRequestedData()).isEqualTo(requestedData);
    }

    private StagePolicies givenStagePoliciesWithRequestedData(RequestedData requestedData) {
        StagePolicies stagePolicies = givenStagePolicies();
        given(stagePolicies.getRequestedData()).willReturn(requestedData);
        return stagePolicies;
    }

    private StagePolicies givenStagePolicies() {
        return mock(StagePolicies.class);
    }

}
