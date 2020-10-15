package uk.co.idv.app.manual.adapter.app;

import lombok.Builder;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;

import java.time.Clock;

@Builder
public class DefaultAppAdapter implements AppAdapter {

    private final RepositoryAdapter repositoryAdapter;

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

}
