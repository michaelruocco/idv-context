package uk.co.idv.method.usecases.otp.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumberMother;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumbers;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumbersMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpPhoneNumbersConverterTest {

    private final OtpPhoneNumberConverter numberConverter = mock(OtpPhoneNumberConverter.class);

    private final OtpPhoneNumbersConverter numbersConverter = new OtpPhoneNumbersConverter(numberConverter);

    @Test
    void shouldConvertNumbersToDeliveryMethods() {
        PhoneDeliveryMethodConfig config = mock(PhoneDeliveryMethodConfig.class);
        OtpPhoneNumber number1 = OtpPhoneNumberMother.localMobile();
        OtpPhoneNumber number2 = OtpPhoneNumberMother.internationalMobile();
        DeliveryMethod expectedMethod1 = givenNumberConvertsTo(number1, config);
        DeliveryMethod expectedMethod2 = givenNumberConvertsTo(number2, config);
        OtpPhoneNumbers numbers = OtpPhoneNumbersMother.with(number1, number2);

        DeliveryMethods methods = numbersConverter.toDeliveryMethods(numbers, config);

        assertThat(methods).containsExactlyInAnyOrder(expectedMethod1, expectedMethod2);
    }

    private DeliveryMethod givenNumberConvertsTo(OtpPhoneNumber number, PhoneDeliveryMethodConfig config) {
        DeliveryMethod deliveryMethod = DeliveryMethodMother.smsWithValue(number);
        given(numberConverter.toDeliveryMethod(number, config)).willReturn(deliveryMethod);
        return deliveryMethod;
    }


}
