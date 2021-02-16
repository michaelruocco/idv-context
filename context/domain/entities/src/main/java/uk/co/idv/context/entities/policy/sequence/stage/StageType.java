package uk.co.idv.context.entities.policy.sequence.stage;

import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;

public interface StageType {

    String getName();

    Eligibility calculateEligibility(Methods methods);

    Duration calculateDuration(Methods methods);

    Methods calculateNextMethods(Methods methods, MethodVerifications verifications);

    boolean calculateSuccessful(Methods methods, MethodVerifications verifications);

    boolean calculateComplete(Methods methods, MethodVerifications verifications);

}
