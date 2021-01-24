package uk.co.idv.context.usecases.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequest;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.context.usecases.context.sequence.stage.StagesBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequenceBuilderTest {

    private final StagesBuilder stagesBuilder = mock(StagesBuilder.class);

    private final SequenceBuilder sequenceBuilder = new SequenceBuilder(stagesBuilder);

    @Test
    void shouldPopulateSequenceNameFromPolicy() {
        SequencesRequest request = mock(SequencesRequest.class);
        String expectedName = "my-name";
        SequencePolicy sequencePolicy = givenSequencePolicyWithName(expectedName);

        Sequence sequence = sequenceBuilder.build(request, sequencePolicy);

        assertThat(sequence.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldPopulateStages() {
        StagePolicies stagePolicies = mock(StagePolicies.class);
        SequencePolicy sequencePolicy = givenSequencePolicyWithStagePolicies(stagePolicies);
        SequencesRequest sequencesRequest = mock(SequencesRequest.class);
        StagesRequest stagesRequest = givenConvertsToStagesRequest(sequencesRequest, sequencePolicy);
        Stages expectedStages = givenRequestBuildsStages(stagesRequest);

        Sequence sequence = sequenceBuilder.build(sequencesRequest, sequencePolicy);

        assertThat(sequence.getStages()).isEqualTo(expectedStages);
    }

    private SequencePolicy givenSequencePolicyWithName(String name) {
        SequencePolicy policy = mock(SequencePolicy.class);
        given(policy.getName()).willReturn(name);
        return policy;
    }

    private SequencePolicy givenSequencePolicyWithStagePolicies(StagePolicies stagePolicies) {
        SequencePolicy policy = mock(SequencePolicy.class);
        given(policy.getStagePolicies()).willReturn(stagePolicies);
        return policy;
    }

    private StagesRequest givenConvertsToStagesRequest(SequencesRequest request, SequencePolicy policy) {
        StagesRequest stagesRequest = mock(StagesRequest.class);
        given(request.toStagesRequest(policy)).willReturn(stagesRequest);
        return stagesRequest;
    }

    private Stages givenRequestBuildsStages(StagesRequest stagesRequest) {
        Stages stages = mock(Stages.class);
        given(stagesBuilder.build(stagesRequest)).willReturn(stages);
        return stages;
    }

}
