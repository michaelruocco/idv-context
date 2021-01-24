package uk.co.idv.context.usecases.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequest;
import uk.co.idv.context.entities.policy.sequence.stage.AllMethodsStageTypeMother;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.policy.MethodPolicies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StageBuilderTest {

    private final MethodsBuilder methodsBuilder = mock(MethodsBuilder.class);

    private final StageBuilder stageBuilder = new StageBuilder(methodsBuilder);

    @Test
    void shouldPopulateStageTypeFromPolicy() {
        StagesRequest request = mock(StagesRequest.class);
        StageType expectedType = AllMethodsStageTypeMother.build();
        StagePolicy stagePolicy = givenStagePolicyWithType(expectedType);

        Stage stage = stageBuilder.build(request, stagePolicy);

        assertThat(stage.getType()).isEqualTo(expectedType);
    }

    @Test
    void shouldPopulateMethods() {
        MethodPolicies methodPolicies = mock(MethodPolicies.class);
        StagePolicy stagePolicy = givenStagePolicyWithMethodPolicies(methodPolicies);
        StagesRequest stagesRequest = mock(StagesRequest.class);
        MethodsRequest methodsRequest = givenConvertsToMethodsRequest(stagesRequest, stagePolicy);
        Methods expectedMethods = givenRequestBuildsMethods(methodsRequest);

        Stage stage = stageBuilder.build(stagesRequest, stagePolicy);

        assertThat(stage.getMethods()).isEqualTo(expectedMethods);
    }

    private StagePolicy givenStagePolicyWithType(StageType type) {
        StagePolicy policy = mock(StagePolicy.class);
        given(policy.getType()).willReturn(type);
        return policy;
    }

    private StagePolicy givenStagePolicyWithMethodPolicies(MethodPolicies methodPolicies) {
        StagePolicy policy = mock(StagePolicy.class);
        given(policy.getMethodPolicies()).willReturn(methodPolicies);
        return policy;
    }

    private MethodsRequest givenConvertsToMethodsRequest(StagesRequest request, StagePolicy policy) {
        MethodsRequest methodsRequest = mock(MethodsRequest.class);
        given(request.toMethodsRequest(policy)).willReturn(methodsRequest);
        return methodsRequest;
    }

    private Methods givenRequestBuildsMethods(MethodsRequest methodsRequest) {
        Methods methods = mock(Methods.class);
        given(methodsBuilder.build(methodsRequest)).willReturn(methods);
        return methods;
    }

}
