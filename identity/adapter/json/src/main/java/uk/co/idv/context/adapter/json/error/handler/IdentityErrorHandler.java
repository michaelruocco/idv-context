package uk.co.idv.context.adapter.json.error.handler;

import uk.co.idv.context.adapter.json.error.aliastype.UnsupportedAliasTypeHandler;
import uk.co.idv.context.adapter.json.error.country.CountryMismatchHandler;
import uk.co.idv.context.adapter.json.error.identitynotfound.IdentityNotFoundHandler;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerHandler;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdHandler;

public class IdentityErrorHandler extends CompositeErrorHandler {

    public IdentityErrorHandler() {
        super(
                new InternalServerHandler(),
                new CannotUpdateIdvIdHandler(),
                new UnsupportedAliasTypeHandler(),
                new IdentityNotFoundHandler(),
                new CountryMismatchHandler()
        );
    }

}
