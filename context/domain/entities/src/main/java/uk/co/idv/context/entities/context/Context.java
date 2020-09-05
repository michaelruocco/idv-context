package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.entities.context.sequence.Sequences;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class Context {

    private final UUID id;
    private final Instant created;
    private final DefaultCreateContextRequest request;
    private final Sequences sequences;

}
