package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.policy.PolicyRequest;

public interface ExternalLockoutRequest extends PolicyRequest {

    Alias getAlias();

}
