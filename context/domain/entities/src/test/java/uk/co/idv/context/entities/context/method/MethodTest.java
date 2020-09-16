package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodTest {

    @Test
    void shouldReturnIsEligibleTrueFromEligibility() {
        Eligibility eligibility = mock(Eligibility.class);
        given(eligibility.isEligible()).willReturn(true);

        Method method = FakeMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIsEligibleFalseFromEligibility() {
        Eligibility eligibility = mock(Eligibility.class);
        given(eligibility.isEligible()).willReturn(false);

        Method method = FakeMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.isEligible()).isFalse();
    }

}
