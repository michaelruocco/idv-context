package uk.co.idv.config.identity.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

import static uk.co.idv.context.usecases.util.DurationCalculator.millisBetweenNowAnd;

@Slf4j
@RequiredArgsConstructor
public class DynamoTables {

    private static final String IDENTITY_TABLE_NAME = "identity";

    private final String environment;
    private final AmazonDynamoDB client;

    public DynamoDB getDynamoDb() {
        return new DynamoDB(client);
    }

    public Table getIdentityTable() {
        String tableName = buildIdentityTableName();
        waitForTableToBeActive(tableName);
        return getTable(tableName);
    }

    public void waitForTablesToBeActive() {
        waitForTableToBeActive(buildIdentityTableName());
    }

    private void waitForTableToBeActive(String tableName) {
        try {
            log.info("waiting for {} table to become active", tableName);
            Instant start = Instant.now();
            TableUtils.waitUntilActive(client, tableName, 30000, 500);
            log.info("table {} took {}ms to become active", tableName, millisBetweenNowAnd(start));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage(), e);
            throw new DynamoException(e);
        }
    }

    private String buildIdentityTableName() {
        return prefixEnvironment(IDENTITY_TABLE_NAME);
    }

    private Table getTable(String tableName) {
        return new DynamoDB(client).getTable(tableName);
    }

    private String prefixEnvironment(String tableName) {
        return String.format("%s-%s", environment, tableName);
    }

}
