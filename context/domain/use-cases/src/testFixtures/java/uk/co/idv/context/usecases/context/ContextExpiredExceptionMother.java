package uk.co.idv.context.usecases.context;

import java.time.Instant;
import java.util.UUID;

public interface ContextExpiredExceptionMother {

    static ContextExpiredException build() {
        return new ContextExpiredException(
                UUID.fromString("c3c6b0af-aa00-4bf8-8810-56f612c7c19e"),
                Instant.parse("2020-11-01T14:40:00.167920Z")
        );
    }

}
