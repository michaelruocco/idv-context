package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class Context {

    private final UUID id;
    private final Instant created;
    private final IdentityCreateContextRequest request;

}
