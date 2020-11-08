package uk.co.idv.app.spring.config.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.identity.config.repository.mongo.MongoIdentityRepositoryConfig;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.mruoc.json.JsonConverter;

@Slf4j
@Configuration
@Profile("!stubbed")
public class SpringMongoRepositoryConfig {

    @Bean
    public IdentityRepository identityRepository(JsonConverter jsonConverter, MongoDatabase database) {
        return new MongoIdentityRepositoryConfig(jsonConverter, database).identityRepository();
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient client) {
        return client.getDatabase(extractDatabaseName(System.getProperty("spring.data.mongodb.uri")));
    }

    private static String extractDatabaseName(String connectionString) {
        String[] tokens = connectionString.split("/");
        return tokens[tokens.length - 1];
    }

}
