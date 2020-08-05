package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.config.identity.respository.IdentityRepositoryConfig;
import uk.co.idv.context.config.identity.respository.dynamo.DynamoIdentityRepositoryConfig;
import uk.co.idv.context.config.identity.respository.dynamo.DynamoTableFactory;
import uk.co.idv.context.config.identity.respository.dynamo.DynamoTables;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.inmemory.InMemoryLockoutRepositoryConfig;
import uk.co.mruoc.json.JsonConverter;

@Configuration
@Profile("!stubbed")
public class SpringDefaultRepositoryConfig {

    @Bean
    public IdentityRepositoryConfig identityRepositoryConfig(JsonConverter jsonConverter) {
        DynamoTableFactory tableFactory = DynamoTableFactory.builder()
                .environment(System.getProperty("environment", "idv-local"))
                .region(System.getProperty("aws.region", "eu-west-1"))
                .endpointUrl(System.getProperty("aws.dynamo.db.endpoint.uri", "http://localhost:4569"))
                .environment(System.getProperty("environment", "idv-local"))
                .build();
        DynamoTables tables = tableFactory.build();
        return new DynamoIdentityRepositoryConfig(jsonConverter, tables);
    }

    @Bean
    public LockoutRepositoryConfig lockoutRepositoryConfig() {
        //TODO replace with redis lockout repository config
        return new InMemoryLockoutRepositoryConfig();
    }

}
