package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequenceTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        Sequence sequence = Sequence.builder()
                .name(name)
                .build();

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnIncompleteEligibleOtpFromMethodsIfPresent() {
        Otp otp = mock(Otp.class);
        Methods methods = mock(Methods.class);
        given(methods.findNextIncompleteEligibleOtp()).willReturn(Optional.of(otp));

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.findNextIncompleteEligibleOtp()).contains(otp);
    }

    @Test
    void shouldReturnEligibleFromMethods() {
        Methods methods = mock(Methods.class);
        given(methods.isEligible()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldReturnCompleteFromMethods() {
        Methods methods = mock(Methods.class);
        given(methods.isComplete()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldReturnSuccessfulFromMethods() {
        Methods methods = mock(Methods.class);
        given(methods.isSuccessful()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isSuccessful()).isTrue();
    }

}
