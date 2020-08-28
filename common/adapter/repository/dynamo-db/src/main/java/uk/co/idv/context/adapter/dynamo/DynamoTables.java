package uk.co.idv.context.adapter.dynamo;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public interface DynamoTables {

    DynamoDB getDynamoDb();

    Table getTable(String tableName);

    void waitForTablesToBeActive(String... tableNames);

}
