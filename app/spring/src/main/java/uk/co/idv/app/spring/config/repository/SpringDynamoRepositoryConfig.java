package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.dynamo.EnvironmentDynamoTablesFactory;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.config.repository.dynamo.DynamoContextRepositoryConfig;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.config.repository.dynamo.DynamoIdentityRepositoryConfig;
import uk.co.idv.lockout.config.repository.AttemptRepositoryConfig;
import uk.co.idv.lockout.config.repository.dynamo.DynamoAttemptRepositoryConfig;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.app.spring.config.repository.EnvironmentLoader.loadEnvironment;

@Configuration
@Profile("!stubbed")
public class SpringDynamoRepositoryConfig {

    @Bean
    public IdentityRepositoryConfig identityRepositoryConfig(JsonConverter jsonConverter, DynamoTables tables) {
        return new DynamoIdentityRepositoryConfig(jsonConverter, tables);
    }

    @Bean
    public AttemptRepositoryConfig attemptRepositoryConfig(JsonConverter jsonConverter, DynamoTables tables) {
        return DynamoAttemptRepositoryConfig.builder()
                .jsonConverter(jsonConverter)
                .tables(tables)
                .build();
    }

    @Bean
    public ContextRepositoryConfig contextRepositoryConfig(JsonConverter jsonConverter, DynamoTables tables) {
        return DynamoContextRepositoryConfig.builder()
                .jsonConverter(jsonConverter)
                .tables(tables)
                .build();
    }

    @Bean
    public DynamoTables dynamoTables() {
        EnvironmentDynamoTablesFactory tableFactory = EnvironmentDynamoTablesFactory.builder()
                .environment(System.getProperty("environment", "idv-local"))
                .region(System.getProperty("aws.region", "eu-west-1"))
                .endpointUrl(System.getProperty("aws.dynamo.db.endpoint.uri", "http://localhost:4569"))
                .environment(loadEnvironment())
                .build();
        return tableFactory.build();
    }

}
