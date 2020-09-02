package uk.co.idv.lockout.entities;

import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.policy.entities.policy.PolicyRequest;

public interface ExternalLockoutRequest extends PolicyRequest {

    Aliases getAliases();

}
