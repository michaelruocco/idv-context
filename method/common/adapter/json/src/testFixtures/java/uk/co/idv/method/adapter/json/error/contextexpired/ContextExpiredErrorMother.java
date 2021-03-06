package uk.co.idv.method.adapter.json.error.contextexpired;

import java.time.Instant;
import java.util.UUID;

public interface ContextExpiredErrorMother {

    static ContextExpiredError contextExpiredError() {
        return ContextExpiredError.builder()
                .id(UUID.fromString("a60687fc-9459-4158-8b57-70d7faa5e9af"))
                .expiry(Instant.parse("2020-11-18T18:48:41.724087Z"))
                .build();
    }

}
