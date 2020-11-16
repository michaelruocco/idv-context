package uk.co.idv.app.manual.adapter.app;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.common.adapter.json.error.handler.CommonApiErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.DefaultAliasFactory;

import java.time.Clock;

@Builder
@Getter
public class DefaultAppAdapter implements AppAdapter {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    @Builder.Default
    private final ErrorHandler errorHandler = new CommonApiErrorHandler();

    @Builder.Default
    private final AliasFactory aliasFactory = new DefaultAliasFactory();

}
