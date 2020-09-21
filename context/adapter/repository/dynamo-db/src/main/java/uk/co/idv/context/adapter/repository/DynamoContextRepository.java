package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.util.Optional;
import java.util.UUID;

@Builder
public class DynamoContextRepository implements ContextRepository {

    private final ContextItemConverter converter;
    private final Table table;

    @Override
    public void save(Context context) {
        Item item = converter.toItem(context);
        table.putItem(item);
    }

    @Override
    public Optional<Context> load(UUID id) {
        PrimaryKey key = converter.toPrimaryKey(id);
        return Optional.ofNullable(table.getItem(key))
                .map(converter::toContext);
    }

}
