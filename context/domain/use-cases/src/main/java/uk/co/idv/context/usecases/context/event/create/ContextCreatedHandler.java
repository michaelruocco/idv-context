package uk.co.idv.context.usecases.context.event.create;

import uk.co.idv.context.entities.context.Context;

public interface ContextCreatedHandler {

    void created(Context context);

}
