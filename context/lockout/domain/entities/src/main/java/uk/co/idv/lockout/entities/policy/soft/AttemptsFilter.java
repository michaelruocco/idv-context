package uk.co.idv.lockout.entities.policy.soft;

import uk.co.idv.lockout.entities.attempt.Attempts;

import java.util.function.UnaryOperator;

public interface AttemptsFilter extends UnaryOperator<Attempts> {

    String getType();

}
