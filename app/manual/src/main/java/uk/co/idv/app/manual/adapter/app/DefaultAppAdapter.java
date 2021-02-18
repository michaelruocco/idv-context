package uk.co.idv.app.manual.adapter.app;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.common.adapter.json.error.handler.CommonApiErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.DefaultAliasFactory;
import uk.co.mruoc.randomvalue.uuid.RandomUuidGenerator;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.time.Clock;

@Builder
@Getter
public class DefaultAppAdapter implements AppAdapter {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    @Builder.Default
    private final UuidGenerator uuidGenerator = new RandomUuidGenerator();

    @Builder.Default
    private final ErrorHandler errorHandler = new CommonApiErrorHandler();

    @Builder.Default
    private final AliasFactory aliasFactory = new DefaultAliasFactory();

}
