package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.sequence.MethodSequence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodEligibleTest {

    private final DeliveryMethodExtractor extractor = mock(DeliveryMethodExtractor.class);

    private final DeliveryMethodEligible deliveryMethodEligible = new DeliveryMethodEligible(extractor);

    @Test
    void shouldNotBeEligibleIfSequenceIsNotEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(false);

        boolean eligible = deliveryMethodEligible.test(sequence);

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldNotBeEligibleIfEligibilityNotComplete() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(deliveryMethod.isEligibilityComplete()).willReturn(false);
        given(deliveryMethod.isEligible()).willReturn(true);
        given(extractor.extract(sequence)).willReturn(deliveryMethod);

        boolean eligible = deliveryMethodEligible.test(sequence);

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldNotBeEligibleIfDeliveryMethodNotEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(deliveryMethod.isEligibilityComplete()).willReturn(true);
        given(deliveryMethod.isEligible()).willReturn(false);
        given(extractor.extract(sequence)).willReturn(deliveryMethod);

        boolean eligible = deliveryMethodEligible.test(sequence);

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldBeEligibleIfEligibilityCompleteAndDeliveryMethodEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(deliveryMethod.isEligibilityComplete()).willReturn(true);
        given(deliveryMethod.isEligible()).willReturn(true);
        given(extractor.extract(sequence)).willReturn(deliveryMethod);

        boolean eligible = deliveryMethodEligible.test(sequence);

        assertThat(eligible).isTrue();
    }

}
