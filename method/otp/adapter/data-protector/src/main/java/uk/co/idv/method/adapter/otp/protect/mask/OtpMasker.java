package uk.co.idv.method.adapter.otp.protect.mask;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpName;
import uk.co.idv.method.usecases.protect.MethodProtector;


@RequiredArgsConstructor
public class OtpMasker implements MethodProtector {

    private final DeliveryMethodMasker deliveryMethodMasker;

    public OtpMasker() {
        this(new DeliveryMethodMasker());
    }

    @Override
    public boolean supports(String name) {
        return OtpName.NAME.equals(name);
    }

    @Override
    public Method apply(Method method) {
        if (isOtp(method)) {
            var otp = (Otp) method;
            return otp.updateDeliveryMethods(deliveryMethodMasker);
        }
        throw new MethodNotOtpException(method.getName());
    }

    private boolean isOtp(Method method) {
        return method instanceof Otp;
    }

}
