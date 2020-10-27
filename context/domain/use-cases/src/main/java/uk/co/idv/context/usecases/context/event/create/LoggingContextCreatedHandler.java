package uk.co.idv.context.usecases.context.event.create;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.MdcPopulator;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class LoggingContextCreatedHandler implements ContextCreatedHandler {

    private final MdcPopulator mdcPopulator;

    public void created(Context context) {
        UUID id = context.getId();
        mdcPopulator.populateContextId(id);
        log.info("context created with id {}", id);
    }

}
