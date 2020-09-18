package uk.co.idv.identity.adapter.json;

import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.identity.adapter.json.error.aliastype.UnsupportedAliasTypeHandler;
import uk.co.idv.identity.adapter.json.error.country.mismatch.CountryMismatchHandler;
import uk.co.idv.identity.adapter.json.error.country.notprovided.CountryNotProvidedHandler;
import uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured.EligibilityNotConfiguredHandler;
import uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundHandler;
import uk.co.idv.identity.adapter.json.error.updateidvid.CannotUpdateIdvIdHandler;

public class IdentityErrorHandler extends CompositeErrorHandler {

    public IdentityErrorHandler() {
        super(
                new CannotUpdateIdvIdHandler(),
                new UnsupportedAliasTypeHandler(),
                new IdentityNotFoundHandler(),
                new CountryMismatchHandler(),
                new CountryNotProvidedHandler(),
                new EligibilityNotConfiguredHandler()
        );
    }

}
