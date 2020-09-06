package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.OtpPhoneNumbersMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumbers;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpPhoneNumbersConverterTest {

    private final OtpPhoneNumberConverter numberConverter = mock(OtpPhoneNumberConverter.class);

    private final OtpPhoneNumbersConverter numbersConverter = new OtpPhoneNumbersConverter(numberConverter);

    @Test
    void shouldConvertNumbersToDeliveryMethods() {
        PhoneDeliveryMethodConfig config = mock(PhoneDeliveryMethodConfig.class);
        OtpPhoneNumber number1 = mock(OtpPhoneNumber.class);
        OtpPhoneNumber number2 = mock(OtpPhoneNumber.class);
        DeliveryMethod expectedMethod1 = givenNumberConvertsTo(number1, config);
        DeliveryMethod expectedMethod2 = givenNumberConvertsTo(number2, config);

        DeliveryMethods methods = numbersConverter.toDeliveryMethods(toNumbers(number1, number2), config);

        assertThat(methods).containsExactly(expectedMethod1, expectedMethod2);
    }

    private DeliveryMethod givenNumberConvertsTo(OtpPhoneNumber number, PhoneDeliveryMethodConfig config) {
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(numberConverter.toDeliveryMethod(number, config)).willReturn(deliveryMethod);
        return deliveryMethod;
    }

    private OtpPhoneNumbers toNumbers(OtpPhoneNumber... numbers) {
        return OtpPhoneNumbersMother.with(numbers);
    }

}
