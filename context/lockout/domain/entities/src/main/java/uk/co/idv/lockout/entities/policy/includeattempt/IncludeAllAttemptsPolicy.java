package uk.co.idv.lockout.entities.policy.includeattempt;

import lombok.Data;
import uk.co.idv.lockout.entities.attempt.Attempts;

@Data
public class IncludeAllAttemptsPolicy implements IncludeAttemptsPolicy {

    public static final String TYPE = "all-attempts";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Attempts apply(Attempts attempts) {
        return attempts;
    }

}
