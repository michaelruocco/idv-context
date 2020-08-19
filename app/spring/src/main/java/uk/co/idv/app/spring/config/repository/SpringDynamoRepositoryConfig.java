package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.dynamo.DynamoTableFactory;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.config.identity.respository.IdentityRepositoryConfig;
import uk.co.idv.context.config.identity.respository.dynamo.DynamoIdentityRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.AttemptRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.dynamo.DynamoAttemptRepositoryConfig;
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
    public DynamoTables dynamoTables() {
        DynamoTableFactory tableFactory = DynamoTableFactory.builder()
                .environment(System.getProperty("environment", "idv-local"))
                .region(System.getProperty("aws.region", "eu-west-1"))
                .endpointUrl(System.getProperty("aws.dynamo.db.endpoint.uri", "http://localhost:4569"))
                .environment(loadEnvironment())
                .build();
        return tableFactory.build();
    }

}
