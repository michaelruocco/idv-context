package uk.co.idv.context.usecases.context.method.otp.delivery;

import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;

import java.util.stream.Collectors;

public class ContextDeliveryMethodReplacer {

    public Context replace(Context context, DeliveryMethods newValues) {
        Sequences sequences = context.getSequences();
        return context.withSequences(replaceDeliveryMethods(sequences, newValues));
    }

    private Sequences replaceDeliveryMethods(Sequences sequences, DeliveryMethods newValues) {
        return new Sequences(sequences.stream()
                .map(sequence -> replaceOtpDeliveryMethods(sequence, newValues))
                .collect(Collectors.toList()));
    }

    private Sequence replaceOtpDeliveryMethods(Sequence sequence, DeliveryMethods newValues) {
        return sequence.withMethods(replaceDeliveryMethods(sequence.getMethods(), newValues));
    }

    private Methods replaceDeliveryMethods(Methods methods, DeliveryMethods newValues) {
        return new Methods(methods.stream()
                .map(method -> replaceDeliveryMethodsIfOtp(method, newValues))
                .collect(Collectors.toList()));
    }

    private Method replaceDeliveryMethodsIfOtp(Method method, DeliveryMethods deliveryMethods) {
        if (method instanceof Otp) {
            Otp otp = (Otp) method;
            return otp.replaceDeliveryMethods(deliveryMethods);
        }
        return method;
    }

}
