package uk.co.idv.context.entities.lockout.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

@Builder
@Data
public class ResetResult {

    private final LockoutState state;
    private final Attempts attemptsToRemove;

}
