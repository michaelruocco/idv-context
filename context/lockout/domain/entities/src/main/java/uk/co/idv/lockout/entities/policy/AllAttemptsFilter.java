package uk.co.idv.lockout.entities.policy;

import lombok.Data;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.soft.AttemptsFilter;

@Data
public class AllAttemptsFilter implements AttemptsFilter {

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
