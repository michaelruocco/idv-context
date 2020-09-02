package uk.co.idv.context.entities.lockout;

import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.context.entities.policy.PolicyRequest;

public interface ExternalLockoutRequest extends PolicyRequest {

    Aliases getAliases();

}
