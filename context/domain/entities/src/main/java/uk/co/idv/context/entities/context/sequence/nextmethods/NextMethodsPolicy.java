package uk.co.idv.context.entities.context.sequence.nextmethods;

import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.MethodVerifications;

public interface NextMethodsPolicy {

    String getType();

    Methods calculateNextMethods(Methods methods, MethodVerifications verifications);

}
