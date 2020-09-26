package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Builder
@Slf4j
public class DynamoContextRepository implements ContextRepository {

    private final ContextItemConverter converter;
    private final Table table;

    @Override
    public void save(Context context) {
        Instant start = Instant.now();
        try {
            Item item = converter.toItem(context);
            log.info("took {}ms to convert to item",
                    millisBetweenNowAnd(start));
            table.putItem(item);
        } finally {
            log.info("took {}ms to save context with id {}",
                    millisBetweenNowAnd(start),
                    context.getId());
        }
    }

    @Override
    public Optional<Context> load(UUID id) {
        Instant start = Instant.now();
        try {
            PrimaryKey key = converter.toPrimaryKey(id);
            return Optional.ofNullable(table.getItem(key))
                    .map(converter::toContext);
        } finally {
            log.info("took {}ms to load context using id {}",
                    millisBetweenNowAnd(start),
                    id);
        }
    }

}
