package uk.co.idv.context.entities.lockout.policy;

import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.attempt.Attempt;

public interface RecordAttemptRequest extends LockoutRequest {

    boolean isSequenceComplete();

    boolean isMethodComplete();

    Attempt getAttempt();

}
