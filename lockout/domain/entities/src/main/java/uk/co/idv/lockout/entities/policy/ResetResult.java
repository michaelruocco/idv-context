package uk.co.idv.lockout.entities.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.lockout.entities.attempt.Attempts;

@Builder
@Data
public class ResetResult {

    private final LockoutState state;
    private final Attempts attemptsToRemove;

}
