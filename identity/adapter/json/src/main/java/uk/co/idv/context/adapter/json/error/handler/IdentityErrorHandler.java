package uk.co.idv.context.adapter.json.error.handler;

import uk.co.idv.context.adapter.json.error.aliastype.UnsupportedAliasTypeHandler;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorHandler;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdHandler;

public class IdentityErrorHandler extends CompositeErrorHandler {

    public IdentityErrorHandler() {
        super(
                new InternalServerErrorHandler(),
                new CannotUpdateIdvIdHandler(),
                new UnsupportedAliasTypeHandler()
        );
    }

}
