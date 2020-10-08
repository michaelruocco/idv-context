package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.sequence.MethodSequence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodEligibilityIncompleteTest {

    private final DeliveryMethodExtractor extractor = mock(DeliveryMethodExtractor.class);

    private final DeliveryMethodEligibilityIncomplete eligibilityIncomplete = new DeliveryMethodEligibilityIncomplete(extractor);

    @Test
    void shouldBeIncompleteIfEligibilityIncomplete() {
        MethodSequence sequence = mock(MethodSequence.class);
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(deliveryMethod.isEligibilityComplete()).willReturn(false);
        given(extractor.extract(sequence)).willReturn(deliveryMethod);

        boolean incomplete = eligibilityIncomplete.test(sequence);

        assertThat(incomplete).isTrue();
    }

    @Test
    void shouldNotBeIncompleteIfEligibilityComplete() {
        MethodSequence sequence = mock(MethodSequence.class);
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(deliveryMethod.isEligibilityComplete()).willReturn(true);
        given(extractor.extract(sequence)).willReturn(deliveryMethod);

        boolean incomplete = eligibilityIncomplete.test(sequence);

        assertThat(incomplete).isFalse();
    }

}
