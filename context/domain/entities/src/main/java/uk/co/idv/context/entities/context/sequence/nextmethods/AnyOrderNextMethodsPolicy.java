package uk.co.idv.context.entities.context.sequence.nextmethods;

import lombok.Data;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.MethodVerifications;

@Data
public class AnyOrderNextMethodsPolicy implements NextMethodsPolicy {

    public static final String TYPE = "any-order";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Methods calculateNextMethods(Methods methods, MethodVerifications verifications) {
        return methods.getAllIncompleteMethods(verifications);
    }

}
