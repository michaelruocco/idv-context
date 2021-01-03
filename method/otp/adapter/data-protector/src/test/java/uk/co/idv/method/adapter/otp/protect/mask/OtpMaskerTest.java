package uk.co.idv.method.adapter.otp.protect.mask;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.Otp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpMaskerTest {

    private final DeliveryMethodMasker deliveryMethodMasker = mock(DeliveryMethodMasker.class);

    private final OtpMasker otpMasker = new OtpMasker(deliveryMethodMasker);

    @Test
    void shouldOnlySupportOtp() {
        assertThat(otpMasker.supports("one-time-passcode")).isTrue();
        assertThat(otpMasker.supports("other-method")).isFalse();
    }

    @Test
    void shouldThrowExceptionIfMethodIsNotOtp() {
        String name = "fake-method";
        Method method = mock(Method.class);
        given(method.getName()).willReturn(name);

        Throwable error = catchThrowable(() -> otpMasker.apply(method));

        assertThat(error)
                .isInstanceOf(MethodNotOtpException.class)
                .hasMessage(name);
    }

    @Test
    void shouldMaskDeliveryMethodsOnOtpMethod() {
        String name = "one-time-passcode";
        Otp otp = mock(Otp.class);
        given(otp.getName()).willReturn(name);
        Otp expectedOtp = mock(Otp.class);
        given(otp.updateDeliveryMethods(deliveryMethodMasker)).willReturn(expectedOtp);

        Method updatedOtp = otpMasker.apply(otp);

        assertThat(updatedOtp).isEqualTo(expectedOtp);
    }

}
