package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HasEligibleMethodTest {

    private static final String METHOD_NAME = "method-name";

    private final HasEligibleMethod hasEligibleMethod = new HasEligibleMethod(METHOD_NAME);

    @Test
    void shouldReturnMethodName() {
        assertThat(hasEligibleMethod.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnNotEligibleIfSequenceNotEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(false);

        boolean eligible = hasEligibleMethod.test(sequence);

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnNotEligibleIfMethodIsNotNext() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        given(sequence.getNext(METHOD_NAME)).willReturn(Optional.empty());

        boolean eligible = hasEligibleMethod.test(sequence);

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnNotEligibleIfMethodIsNextButNotEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        Method method = FakeMethodMother.ineligible();
        given(sequence.getNext(METHOD_NAME)).willReturn(Optional.of(method));

        boolean eligible = hasEligibleMethod.test(sequence);

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnEligibleIfMethodIsNextAndEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        Method method = FakeMethodMother.eligible();
        given(sequence.getNext(METHOD_NAME)).willReturn(Optional.of(method));

        boolean eligible = hasEligibleMethod.test(sequence);

        assertThat(eligible).isTrue();
    }

}
