package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static uk.co.idv.context.usecases.util.DurationCalculator.millisBetweenNowAnd;

@Slf4j
@RequiredArgsConstructor
public class DynamoIdentityRepository implements IdentityRepository {

    private final IdentityConverter converter;
    private final DynamoDB dynamoDb;

    @Override
    public void create(Identity updated) {
        batchUpdateItems(updated);
    }

    @Override
    public void update(Identity updated, Identity existing) {
        deleteEntriesForRemovedAliases(updated, existing);
        batchUpdateItems(updated);
    }

    @Override
    public Identities load(Aliases aliases) {
        Instant start = Instant.now();
        TableKeysAndAttributes keysAndAttributes = converter.toKeys(aliases);
        try {
            BatchGetItemOutcome outcome = dynamoDb.batchGetItem(keysAndAttributes);
            errorIfUnprocessedItems(outcome);
            return converter.toIdentities(outcome);
        } finally {
            log.info("took {}ms to load identities using aliases {}",
                    millisBetweenNowAnd(start),
                    aliases);
        }
    }

    @Override
    public void delete(Aliases aliases) {
        Instant start = Instant.now();
        TableWriteItems items = converter.toBatchDeleteItems(aliases);
        try {
            BatchWriteItemOutcome outcome = dynamoDb.batchWriteItem(items);
            errorIfUnprocessedItems(outcome);
        } finally {
            log.info("took {}ms to delete {} items using aliases {} items",
                    millisBetweenNowAnd(start),
                    items.getPrimaryKeysToDelete().size(),
                    aliases);
        }
    }

    private void batchUpdateItems(Identity updated) {
        Instant start = Instant.now();
        TableWriteItems items = converter.toBatchUpdateItems(updated);
        try {
            BatchWriteItemOutcome outcome = dynamoDb.batchWriteItem(items);
            errorIfUnprocessedItems(outcome);
        } finally {
            log.info("took {}ms to update {} items for identity {}",
                    millisBetweenNowAnd(start),
                    items.getItemsToPut().size(),
                    updated);
        }
    }

    private void deleteEntriesForRemovedAliases(Identity updated, Identity existing) {
        Aliases aliasesToRemove = existing.getAliasesNotPresent(updated);
        log.info("aliases to remove {}", aliasesToRemove);
        if (aliasesToRemove.isEmpty()) {
            return;
        }
        delete(aliasesToRemove);
    }

    private void errorIfUnprocessedItems(BatchGetItemOutcome outcome) {
        Map<String, KeysAndAttributes> keys = outcome.getUnprocessedKeys();
        if (!keys.isEmpty()) {
            throw new DynamoBatchGetException(outcome);
        }
    }

    private void errorIfUnprocessedItems(BatchWriteItemOutcome outcome) {
        Map<String, List<WriteRequest>> requests = outcome.getUnprocessedItems();
        if (!requests.isEmpty()) {
            throw new DynamoBatchWriteException(outcome);
        }
    }

}
