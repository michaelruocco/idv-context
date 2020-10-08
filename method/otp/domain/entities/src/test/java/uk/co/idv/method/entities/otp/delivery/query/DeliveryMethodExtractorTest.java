package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodExtractorTest {

    private final UUID extractorId = UUID.fromString("0c207ec2-5e3e-488c-bdcb-e40576dac35d");

    private final DeliveryMethodExtractor extractor = new DeliveryMethodExtractor(extractorId);

    @Test
    void shouldReturnEmptyIfNoNextMethod() {
        MethodSequence sequence = givenSequenceWithNoNextMethod();

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(sequence);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfNextMethodNotOtp() {
        MethodSequence sequence = givenSequenceWithNextMethodNotOtp();

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(sequence);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfMethodDoesNotHaveDeliveryMethodWithId() {
        MethodSequence sequence = givenSequenceWithDeliveryMethodWithOtherId();

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(sequence);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnOptionalDeliveryMethodIfIdMatches() {
        DeliveryMethod expected = DeliveryMethodMother.withId(extractorId);
        MethodSequence sequence = givenSequenceWithDeliveryMethod(expected);

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(sequence);

        assertThat(deliveryMethod).contains(expected);
    }

    @Test
    void shouldThrowExceptionIfNoNextMethod() {
        MethodSequence sequence = givenSequenceWithNoNextMethod();

        Throwable error = catchThrowable(() -> extractor.extract(sequence));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(extractorId.toString());
    }

    @Test
    void shouldThrowExceptionIfNextMethodIsNotOtp() {
        MethodSequence sequence = givenSequenceWithNextMethodNotOtp();

        Throwable error = catchThrowable(() -> extractor.extract(sequence));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(extractorId.toString());
    }

    @Test
    void shouldThrowExceptionIfDoesNotHaveDeliveryMethodWithId() {
        MethodSequence sequence = givenSequenceWithDeliveryMethodWithOtherId();

        Throwable error = catchThrowable(() -> extractor.extract(sequence));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(extractorId.toString());
    }

    @Test
    void shouldReturnDeliveryMethodIfIdMatches() {
        DeliveryMethod expected = DeliveryMethodMother.withId(extractorId);
        MethodSequence sequence = givenSequenceWithDeliveryMethod(expected);

        DeliveryMethod deliveryMethod = extractor.extract(sequence);

        assertThat(deliveryMethod).isEqualTo(expected);
    }

    private MethodSequence givenSequenceWithNoNextMethod() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.getNext()).willReturn(Optional.empty());
        return sequence;
    }

    private MethodSequence givenSequenceWithNextMethodNotOtp() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.getNext()).willReturn(Optional.of(mock(Method.class)));
        return sequence;
    }

    private MethodSequence givenSequenceWithDeliveryMethodWithOtherId() {
        UUID otherId = UUID.fromString("1c9f6e5f-f1b6-4b3b-a9fc-d0dc25133e59");
        return givenSequenceWithDeliveryMethod(DeliveryMethodMother.withId(otherId));
    }

    private MethodSequence givenSequenceWithDeliveryMethod(DeliveryMethod deliveryMethod) {
        MethodSequence sequence = mock(MethodSequence.class);
        Method method = OtpMother.withDeliveryMethod(deliveryMethod);
        given(sequence.getNext()).willReturn(Optional.of(method));
        return sequence;
    }

}
