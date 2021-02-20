package uk.co.idv.lockout.entities.policy.includeattempt;

import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.function.UnaryOperator;

public interface IncludeAttemptsPolicy extends UnaryOperator<Attempts> {

    String getType();

}
