package uk.co.idv.context.usecases.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.policy.MethodPolicies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequenceBuilderTest {

    private final MethodsBuilder methodsBuilder = mock(MethodsBuilder.class);

    private final SequenceBuilder sequenceBuilder = new SequenceBuilder(methodsBuilder);

    @Test
    void shouldPopulateSequenceNameFromPolicy() {
        SequencesRequest request = mock(SequencesRequest.class);
        String expectedName = "my-name";

        Sequence sequence = sequenceBuilder.build(request, givenSequencePolicyWithName(expectedName));

        assertThat(sequence.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldPopulateMethods() {
        MethodPolicies methodPolicies = mock(MethodPolicies.class);
        SequencePolicy sequencePolicy = givenSequencePolicyWithMethodPolicies(methodPolicies);
        SequencesRequest sequencesRequest = mock(SequencesRequest.class);
        MethodsRequest methodsRequest = givenConvertsToMethodsRequest(sequencesRequest, sequencePolicy);
        Methods expectedMethods = givenRequestBuildsMethods(methodsRequest);

        Sequence sequence = sequenceBuilder.build(sequencesRequest, sequencePolicy);

        assertThat(sequence.getMethods()).isEqualTo(expectedMethods);
    }

    private SequencePolicy givenSequencePolicyWithName(String name) {
        SequencePolicy policy = mock(SequencePolicy.class);
        given(policy.getName()).willReturn(name);
        return policy;
    }

    private SequencePolicy givenSequencePolicyWithMethodPolicies(MethodPolicies methodPolicies) {
        SequencePolicy policy = mock(SequencePolicy.class);
        given(policy.getMethodPolicies()).willReturn(methodPolicies);
        return policy;
    }

    private MethodsRequest givenConvertsToMethodsRequest(SequencesRequest request, SequencePolicy policy) {
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
