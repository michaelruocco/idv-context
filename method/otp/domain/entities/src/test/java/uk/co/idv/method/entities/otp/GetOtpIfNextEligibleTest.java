package uk.co.idv.method.entities.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetOtpIfNextEligibleTest {

    private final GetOtpIfNextEligible getOtpIfNextEligible = new GetOtpIfNextEligible();

    @Test
    void shouldReturnEmptyIfSequenceNotEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(false);

        Optional<Otp> otp = getOtpIfNextEligible.apply(sequence);

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfNoNextMethod() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        given(sequence.getNext()).willReturn(Optional.empty());

        Optional<Otp> otp = getOtpIfNextEligible.apply(sequence);

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfNextMethodIsNotOtp() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        Method method = mock(Method.class);
        given(sequence.getNext()).willReturn(Optional.of(method));

        Optional<Otp> otp = getOtpIfNextEligible.apply(sequence);

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldReturnOtpIfNextMethodIsOtp() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        Otp expected = mock(Otp.class);
        given(sequence.getNext()).willReturn(Optional.of(expected));

        Optional<Otp> otp = getOtpIfNextEligible.apply(sequence);

        assertThat(otp).contains(expected);
    }

}
