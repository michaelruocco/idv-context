package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.dynamo.EnvironmentDynamoTablesFactory;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.config.repository.dynamo.DynamoContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
//import uk.co.idv.identity.config.repository.dynamo.DynamoIdentityRepositoryConfig;
//import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.lockout.config.repository.dynamo.DynamoAttemptRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.mruoc.json.JsonConverter;

import java.time.Clock;

import static uk.co.idv.app.spring.config.repository.EnvironmentLoader.loadEnvironment;

@Configuration
@Profile("!stubbed")
public class SpringDynamoRepositoryConfig {

    //@Bean
    //public IdentityRepository identityRepository(JsonConverter jsonConverter, DynamoTables tables) {
    //    return new DynamoIdentityRepositoryConfig(jsonConverter, tables).identityRepository();
    //}

    @Bean
    public AttemptRepository attemptRepository(JsonConverter jsonConverter, DynamoTables tables) {
        return DynamoAttemptRepositoryConfig.builder()
                .jsonConverter(jsonConverter)
                .tables(tables)
                .build()
                .attemptRepository();
    }

    @Bean
    public ContextRepository contextRepository(Clock clock,
                                               JsonConverter jsonConverter,
                                               DynamoTables tables) {
        return DynamoContextRepositoryConfig.builder()
                .clock(clock)
                .jsonConverter(jsonConverter)
                .tables(tables)
                .build()
                .contextRepository();
    }

    @Bean
    public DynamoTables dynamoTables() {
        EnvironmentDynamoTablesFactory tableFactory = EnvironmentDynamoTablesFactory.builder()
                .region(System.getProperty("aws.region", "eu-west-1"))
                .endpointUrl(System.getProperty("aws.dynamo.db.endpoint.uri", "http://localhost:4569"))
                .environment(loadEnvironment())
                .build();
        return tableFactory.build();
    }

}
