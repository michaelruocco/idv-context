package uk.co.idv.config.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

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

    public void waitForIdentityTableToBeActive() {
        waitForTableToBeActive(buildIdentityTableName());
    }

    public void waitForTableToBeActive(String tableName) {
        try {
            log.info("waiting for {} table to become active", tableName);
            Instant start = Instant.now();
            TableUtils.waitUntilActive(client, tableName, 59000, 1000);
            log.info("table {} took {}ms to become active", tableName, Duration.between(start, Instant.now()).toMillis());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
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
