package uk.co.idv.method.usecases;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodBuildersTest {

    @Test
    void shouldReturnEmptyIfNoBuildersSupportPolicy() {
        MethodPolicy policy = mock(MethodPolicy.class);
        MethodBuilder builder1 = givenMethodBuilderThatDoesNotSupport(policy);
        MethodBuilder builder2 = givenMethodBuilderThatDoesNotSupport(policy);
        MethodBuilders builders = new MethodBuilders(builder1, builder2);

        Optional<MethodBuilder> found = builders.findBuilderSupporting(policy);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldReturnFirstBuilderThatSupportsPolicy() {
        MethodPolicy policy = mock(MethodPolicy.class);
        MethodBuilder nonSupporting = givenMethodBuilderThatDoesNotSupport(policy);
        MethodBuilder supporting1 = givenMethodBuilderThatSupports(policy);
        MethodBuilder supporting2 = givenMethodBuilderThatSupports(policy);
        MethodBuilders builders = new MethodBuilders(nonSupporting, supporting1, supporting2);

        Optional<MethodBuilder> found = builders.findBuilderSupporting(policy);

        assertThat(found).contains(supporting1);
    }

    private MethodBuilder givenMethodBuilderThatSupports(MethodPolicy policy) {
        MethodBuilder builder = givenMethodBuilder();
        given(builder.supports(policy)).willReturn(true);
        return builder;
    }

    private MethodBuilder givenMethodBuilderThatDoesNotSupport(MethodPolicy policy) {
        MethodBuilder builder = givenMethodBuilder();
        given(builder.supports(policy)).willReturn(false);
        return builder;
    }

    private MethodBuilder givenMethodBuilder() {
        return mock(MethodBuilder.class);
    }

}
