package uk.co.idv.lockout.entities.policy;

import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.attempt.Attempt;

public interface RecordAttemptRequest extends LockoutRequest {

    boolean isSequenceComplete();

    boolean isMethodComplete();

    Attempt getAttempt();

}
