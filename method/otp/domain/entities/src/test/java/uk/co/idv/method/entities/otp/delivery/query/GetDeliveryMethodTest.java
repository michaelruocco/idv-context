package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class GetDeliveryMethodTest {

    private final DeliveryMethodExtractor extractor = mock(DeliveryMethodExtractor.class);

    private final GetDeliveryMethod getDeliveryMethod = new GetDeliveryMethod(extractor);

    @Test
    void shouldReturnEmptyIfSequenceIsNotEligible() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(false);

        Optional<DeliveryMethod> deliveryMethod = getDeliveryMethod.apply(sequence);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnDeliveryMethodFromExtractor() {
        MethodSequence sequence = mock(MethodSequence.class);
        given(sequence.isEligible()).willReturn(true);
        DeliveryMethod expected = mock(DeliveryMethod.class);
        given(extractor.extractOptional(sequence)).willReturn(Optional.of(expected));

        Optional<DeliveryMethod> deliveryMethod = getDeliveryMethod.apply(sequence);

        assertThat(deliveryMethod).contains(expected);
    }

}
