package uk.co.idv.method.usecases.otp.delivery;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;

import java.util.function.UnaryOperator;

@Data
@RequiredArgsConstructor
public class OtpDeliveryMethodReplacer implements UnaryOperator<Method> {

    private final DeliveryMethods deliveryMethods;

    @Override
    public Method apply(Method method) {
        if (method instanceof Otp) {
            Otp otp = (Otp) method;
            return otp.replaceDeliveryMethods(deliveryMethods);
        }
        return method;
    }

}
