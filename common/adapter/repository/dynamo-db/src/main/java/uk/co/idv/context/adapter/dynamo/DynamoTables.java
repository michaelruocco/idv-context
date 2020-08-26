package uk.co.idv.context.adapter.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.stream.Stream;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Slf4j
@RequiredArgsConstructor
public class DynamoTables {

    private final String environment;
    private final AmazonDynamoDB client;

    public DynamoDB getDynamoDb() {
        return new DynamoDB(client);
    }

    public Table getEnvironmentTable(String tableName) {
        String environmentTableName = prefixEnvironment(tableName);
        waitForTableToBeActive(environmentTableName);
        return getTable(environmentTableName);
    }

    public void waitForEnvironmentTablesToBeActive(String... tableNames) {
        Stream.of(tableNames)
                .map(this::prefixEnvironment)
                .forEach(this::waitForTableToBeActive);
    }

    private void waitForTableToBeActive(String tableName) {
        try {
            log.info("waiting for {} table to become active", tableName);
            Instant start = Instant.now();
            TableUtils.waitUntilActive(client, tableName, 30000, 500);
            log.info("table {} took {}ms to become active", tableName, millisBetweenNowAnd(start));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("thread interrupted waiting for table {} to become active", tableName, e);
            throw new WaitForTableToBeActiveException(e);
        }
    }

    private Table getTable(String tableName) {
        return new DynamoDB(client).getTable(tableName);
    }

    private String prefixEnvironment(String tableName) {
        return String.format("%s-%s", environment, tableName);
    }

}
