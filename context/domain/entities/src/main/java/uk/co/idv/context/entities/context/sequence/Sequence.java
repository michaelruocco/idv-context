package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.util.Optional;

@Builder
@Data
public class Sequence {

    private final String name;
    private final Methods methods;

    public Optional<Otp> findNextIncompleteEligibleOtp() {
        return methods.findNextIncompleteEligibleOtp();
    }

    public boolean isEligible() {
        return methods.isEligible();
    }

    public boolean isComplete() { return methods.isComplete(); }

    public boolean isSuccessful() { return methods.isSuccessful(); }

}
