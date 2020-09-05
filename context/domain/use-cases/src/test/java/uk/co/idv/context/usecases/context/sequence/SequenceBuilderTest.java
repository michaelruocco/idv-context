package uk.co.idv.context.usecases.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.identity.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequenceBuilderTest {

    private final MethodsBuilder methodsBuilder = mock(MethodsBuilder.class);

    private final SequenceBuilder sequenceBuilder = new SequenceBuilder(methodsBuilder);

    @Test
    void shouldPopulateSequenceNameFromPolicy() {
        Identity identity = mock(Identity.class);
        String expectedName = "my-name";

        Sequence sequence = sequenceBuilder.build(identity, givenSequencePolicyWithName(expectedName));

        assertThat(sequence.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldPopulateMethods() {
        Identity identity = mock(Identity.class);
        MethodPolicies methodPolicies = mock(MethodPolicies.class);
        Methods expectedMethods = givenPoliciesConvertToMethods(identity, methodPolicies);

        Sequence sequence = sequenceBuilder.build(identity, givenSequencePolicyWithMethodPolicies(methodPolicies));

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

    private Methods givenPoliciesConvertToMethods(Identity identity, MethodPolicies methodPolicies) {
        Methods methods = mock(Methods.class);
        given(methodsBuilder.build(identity, methodPolicies)).willReturn(methods);
        return methods;
    }

}
