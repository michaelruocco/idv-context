package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;

import java.util.Optional;

@Builder
public class DynamoAttemptRepository implements AttemptRepository {

    private final AttemptItemConverter converter;
    private final Table table;

    @Override
    public void save(Attempts attempts) {
        Item item = converter.toItem(attempts);
        table.putItem(item);
    }

    @Override
    public Optional<Attempts> load(IdvId idvId) {
        PrimaryKey key = converter.toPrimaryKey(idvId);
        return Optional.ofNullable(table.getItem(key))
                .map(converter::toAttempts);
    }

}
