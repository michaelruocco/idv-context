package uk.co.idv.lockout.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Builder
@Slf4j
public class DynamoAttemptRepository implements AttemptRepository {

    private final AttemptItemConverter converter;
    private final Table table;
    private final DynamoDB dynamoDb;

    @Override
    public void save(Attempts attempts) {
        Instant start = Instant.now();
        try {
            Item item = converter.toItem(attempts);
            table.putItem(item);
        } finally {
            log.info("took {}ms to save attempts with idv id {}",
                    millisBetweenNowAnd(start),
                    attempts.getIdvId());
        }
    }

    @Override
    public Optional<Attempts> load(IdvId idvId) {
        Instant start = Instant.now();
        try {
            PrimaryKey key = converter.toPrimaryKey(idvId);
            return Optional.ofNullable(table.getItem(key))
                    .map(converter::toAttempts);
        } finally {
            log.info("took {}ms to load attempts using idvId {}",
                    millisBetweenNowAnd(start),
                    idvId);
        }
    }

    @Override
    public void delete(Collection<IdvId> idvIds) {
        Instant start = Instant.now();
        try {
            if (!idvIds.isEmpty()) {
                dynamoDb.batchWriteItem(converter.toBatchDeleteItems(idvIds));
            }
        } finally {
            log.info("took {}ms to delete attempts with idvIds {}",
                    millisBetweenNowAnd(start),
                    idvIds);
        }
    }

}
