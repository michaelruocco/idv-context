package uk.co.idv.context.adapter.json.lockout.error.lockedout;

import lombok.Getter;
import uk.co.idv.context.adapter.json.error.DefaultApiError;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

import java.util.Map;

@Getter
public class LockedOutError extends DefaultApiError {

    private static final int STATUS = 423;
    private static final String TITLE = "Identity locked";

    public LockedOutError(LockoutState state) {
        super(STATUS, TITLE, toMessage(state.getIdvId()), toMap(state));
    }

    private static String toMessage(IdvId idvId) {
        return String.format("Identity with %s locked", idvId.format());
    }

    private static Map<String, Object> toMap(LockoutState state) {
        return Map.of("state", state);
    }

}
