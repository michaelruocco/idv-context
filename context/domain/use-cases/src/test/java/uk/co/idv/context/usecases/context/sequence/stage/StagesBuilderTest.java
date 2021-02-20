package uk.co.idv.context.usecases.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequest;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequestMother;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StagesBuilderTest {

    private final StageBuilder stageBuilder = mock(StageBuilder.class);

    private final StagesBuilder stagesBuilder = new StagesBuilder(stageBuilder);

    @Test
    void shouldConvertStagePoliciesToStages() {
        StagePolicy policy1 = mock(StagePolicy.class);
        StagePolicy policy2 = mock(StagePolicy.class);
        StagesRequest request = StagesRequestMother.withPolicies(policy1, policy2);
        Stage expectedStage1 = givenPolicyConvertsToStage(request, policy1);
        Stage expectedStage2 = givenPolicyConvertsToStage(request, policy2);

        Stages stages = stagesBuilder.build(request);

        assertThat(stages).containsExactly(
                expectedStage1,
                expectedStage2
        );
    }

    private Stage givenPolicyConvertsToStage(StagesRequest request, StagePolicy policy) {
        Stage stage = mock(Stage.class);
        given(stageBuilder.build(request, policy)).willReturn(stage);
        return stage;
    }

}
