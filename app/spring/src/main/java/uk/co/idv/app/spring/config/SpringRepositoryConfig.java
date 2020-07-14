package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.config.repository.RepositoryConfig;
import uk.co.idv.config.repository.dynamo.DynamoRepositoryConfig;
import uk.co.idv.config.repository.dynamo.DynamoTableFactory;
import uk.co.idv.config.repository.dynamo.DynamoTables;
import uk.co.idv.config.repository.inmemory.InMemoryRepositoryConfig;
import uk.co.mruoc.json.JsonConverter;

@Configuration
public class SpringRepositoryConfig {

    @Bean
    @Profile("!stubbed")
    public RepositoryConfig dynamoRepositoryConfig(JsonConverter jsonConverter) {
        DynamoTableFactory tableFactory = DynamoTableFactory.builder()
                .endpointUrl(System.getProperty("aws.dynamo.db.endpoint.uri", "http://localhost:4569"))
                .environment(System.getProperty("environment", "idv-local"))
                .build();
        DynamoTables tables = tableFactory.build();
        return new DynamoRepositoryConfig(jsonConverter, tables);
    }

    @Bean
    @Profile("stubbed")
    public RepositoryConfig inMemoryRepositoryConfig() {
        return new InMemoryRepositoryConfig();
    }

}
