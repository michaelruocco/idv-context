package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class DynamoIdentityRepository implements IdentityRepository {

    private final IdentityConverter identityConverter;
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
        TableKeysAndAttributes keysAndAttributes = identityConverter.toKeys(aliases);
        BatchGetItemOutcome outcome = dynamoDb.batchGetItem(keysAndAttributes);
        Identities identities = identityConverter.toIdentities(outcome);
        log.info("took {}ms to load {} identities using aliases {}",
                millisBetweenNowAnd(start),
                identities.size(),
                aliases);
        return identities;
    }

    @Override
    public void delete(Aliases aliases) {
        Instant start = Instant.now();
        TableWriteItems items = identityConverter.toBatchDeleteItems(aliases);
        dynamoDb.batchWriteItem(items);
        log.info("took {}ms to delete {} items using aliases {} items",
                millisBetweenNowAnd(start),
                items.getPrimaryKeysToDelete().size(),
                aliases);
    }

    private void batchUpdateItems(Identity updated) {
        Instant start = Instant.now();
        TableWriteItems items = identityConverter.toBatchUpdateItems(updated);
        dynamoDb.batchWriteItem(items);
        log.info("took {}ms to update {} items for identity {}",
                millisBetweenNowAnd(start),
                items.getItemsToPut().size(),
                updated);
    }

    private void deleteEntriesForRemovedAliases(Identity updated, Identity existing) {
        Aliases aliasesToRemove = existing.getAliasesNotPresent(updated);
        log.info("aliases to remove {}", aliasesToRemove);
        if (aliasesToRemove.isEmpty()) {
            return;
        }
        delete(aliasesToRemove);
    }

    private long millisBetweenNowAnd(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }

}
