package uk.co.idv.identity.config;

import uk.co.idv.identity.adapter.json.IdentityErrorHandler;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;

public interface IdentityConfig {
    FindIdentity findIdentity();

    CreateEligibility createEligibility();

    IdentityService identityService();

    AliasFactory aliasFactory();

    IdentityErrorHandler errorHandler();
}
