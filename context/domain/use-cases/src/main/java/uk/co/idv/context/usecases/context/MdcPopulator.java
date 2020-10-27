package uk.co.idv.context.usecases.context;

import org.slf4j.MDC;

import java.util.UUID;

public class MdcPopulator {

    private static final String CONTEXT_ID_NAME = "context-id";

    public void populateContextId(UUID id) {
        MDC.put(CONTEXT_ID_NAME, id.toString());
    }

}
