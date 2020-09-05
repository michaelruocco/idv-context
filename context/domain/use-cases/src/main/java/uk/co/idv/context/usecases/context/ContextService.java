package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;

import java.time.Clock;

@Builder
public class ContextService {

    private final IdGenerator idGenerator;
    private final Clock clock;
    private final SequencesBuilder sequencesBuilder;

    public Context create(DefaultCreateContextRequest request) {
        return Context.builder()
                .id(idGenerator.generate())
                .created(clock.instant())
                .request(request)
                .sequences(sequencesBuilder.build(request.getIdentity(), request.getSequencePolicies()))
                .build();
    }

}
