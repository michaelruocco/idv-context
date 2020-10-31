package uk.co.idv.app.manual.adapter.app;

import lombok.Builder;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.common.adapter.json.error.handler.CommonApiErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
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

    @Builder.Default
    private final ErrorHandler errorHandler = new CommonApiErrorHandler();

    @Override
    public Clock getClock() {
        return clock;
    }

    @Override
    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

}
