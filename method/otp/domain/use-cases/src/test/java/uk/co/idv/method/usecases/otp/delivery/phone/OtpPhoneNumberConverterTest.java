package uk.co.idv.method.usecases.otp.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumberMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpPhoneNumberConverterTest {

    private final PhoneDeliveryMethodConfig config = mock(PhoneDeliveryMethodConfig.class);
    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);
    private final OtpPhoneNumberEligibilityCalculator eligibilityCalculator = mock(OtpPhoneNumberEligibilityCalculator.class);

    private final OtpPhoneNumberConverter converter = OtpPhoneNumberConverter.builder()
            .uuidGenerator(uuidGenerator)
            .eligibilityCalculator(eligibilityCalculator)
            .build();

    @Test
    void shouldPopulateIdOnDeliveryMethod() {
        UUID expectedId = UUID.randomUUID();
        given(uuidGenerator.generate()).willReturn(expectedId);
        OtpPhoneNumber number = OtpPhoneNumberMother.localMobile();

        DeliveryMethod method = converter.toDeliveryMethod(number, config);

        assertThat(method.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldPopulateTypeOnDeliveryMethod() {
        String expectedType = "sms";
        given(config.getType()).willReturn(expectedType);
        OtpPhoneNumber number = OtpPhoneNumberMother.localMobile();

        DeliveryMethod method = converter.toDeliveryMethod(number, config);

        assertThat(method.getType()).isEqualTo(expectedType);
    }

    @Test
    void shouldPopulateValueOnDeliveryMethod() {
        OtpPhoneNumber number = OtpPhoneNumberMother.localMobile();

        DeliveryMethod method = converter.toDeliveryMethod(number, config);

        assertThat(method.getValue()).isEqualTo(number.getValue());
    }

    @Test
    void shouldPopulateLastUpdatedOnDeliveryMethod() {
        OtpPhoneNumber number = OtpPhoneNumberMother.localMobile();

        DeliveryMethod method = converter.toDeliveryMethod(number, config);

        assertThat(method.getLastUpdated()).isEqualTo(number.getLastUpdated());
    }

    @Test
    void shouldPopulatedLastUpdatedAsEmptyOnDeliveryMethodIfNotPresent() {
        OtpPhoneNumber number = OtpPhoneNumberMother.withoutLastUpdated();

        DeliveryMethod method = converter.toDeliveryMethod(number, config);

        assertThat(method.getLastUpdated()).isEmpty();
    }

    @Test
    void shouldPopulateEligibilityOnDeliveryMethod() {
        OtpPhoneNumber number = OtpPhoneNumberMother.localMobile();
        Eligibility expectedEligibility = givenCalculatedEligibility(number);

        DeliveryMethod method = converter.toDeliveryMethod(number, config);

        assertThat(method.getEligibility()).isEqualTo(expectedEligibility);
    }

    private Eligibility givenCalculatedEligibility(OtpPhoneNumber number) {
        Eligibility eligibility = mock(Eligibility.class);
        given(eligibilityCalculator.toEligibility(number, config)).willReturn(eligibility);
        return eligibility;
    }

}
