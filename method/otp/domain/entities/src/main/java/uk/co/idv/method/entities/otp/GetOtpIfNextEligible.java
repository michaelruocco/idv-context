package uk.co.idv.method.entities.otp;

import uk.co.idv.method.entities.method.MethodToType;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;
import java.util.function.Function;

public class GetOtpIfNextEligible implements Function<MethodSequence, Optional<Otp>> {

    @Override
    public Optional<Otp> apply(MethodSequence sequence) {
        if (!sequence.isEligible()) {
            return Optional.empty();
        }
        return sequence.getNext().flatMap(new MethodToType<>(Otp.class));
    }

}
