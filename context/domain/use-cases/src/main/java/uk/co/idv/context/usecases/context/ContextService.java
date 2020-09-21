package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;

import java.time.Clock;
import java.util.UUID;

@Builder
public class ContextService {

    @Builder.Default
    private final IdGenerator idGenerator= new RandomIdGenerator();

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    private final SequencesBuilder sequencesBuilder;
    private final ContextRepository repository;

    public Context create(DefaultCreateContextRequest request) {
        Context context = Context.builder()
                .id(idGenerator.generate())
                .created(clock.instant())
                .request(request)
                .sequences(sequencesBuilder.build(request.getIdentity(), request.getSequencePolicies()))
                .build();
        repository.save(context);
        return context;
    }

    public Context find(UUID id) {
        return repository.load(id).orElseThrow(() -> new ContextNotFoundException(id));
    }

}
