package uk.co.idv.context.entities.context;

import java.util.UUID;

public interface ContextNotFoundExceptionMother {

    static ContextNotFoundException build() {
        return new ContextNotFoundException(UUID.fromString("a104ab7a-39c7-4adf-9f7a-7f1e749a1c1c"));
    }

}
