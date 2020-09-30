package uk.co.idv.context.config.repository.dynamo;

import lombok.Builder;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.adapter.dynamo.TimeToLiveCalculator;
import uk.co.idv.context.adapter.repository.ContextItemConverter;
import uk.co.idv.context.adapter.repository.DynamoContextRepository;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.mruoc.json.JsonConverter;

import java.time.Clock;
import java.time.Duration;

@Builder
public class DynamoContextRepositoryConfig implements ContextRepositoryConfig {

    public static final String CONTEXT_TABLE_NAME = "context";

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    private final JsonConverter jsonConverter;
    private final DynamoTables tables;

    @Override
    public ContextRepository contextRepository() {
        return DynamoContextRepository.builder()
                .converter(contextItemConverter())
                .table(tables.getTable(CONTEXT_TABLE_NAME))
                .build();
    }

    private ContextItemConverter contextItemConverter() {
        return ContextItemConverter.builder()
                .jsonConverter(jsonConverter)
                .timeToLiveCalculator(timeToLiveCalculator())
                .build();
    }

    private TimeToLiveCalculator timeToLiveCalculator() {
        return TimeToLiveCalculator.builder()
                .clock(clock)
                .timeToLive(Duration.ofDays(1))
                .build();
    }

}
