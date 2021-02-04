package uk.co.idv.app.spring.config.repository;

import com.github.mongobee.Mongobee;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.config.repository.mongo.MongoContextChangeLog;
import uk.co.idv.context.config.repository.mongo.MongoContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.identity.config.repository.mongo.MongoIdentityChangeLog;
import uk.co.idv.identity.config.repository.mongo.MongoIdentityRepositoryConfig;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.lockout.config.repository.mongo.MongoAttemptChangeLog;
import uk.co.idv.lockout.config.repository.mongo.MongoAttemptRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.mruoc.json.JsonConverter;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@Profile("!stubbed")
public class SpringMongoRepositoryConfig {

    @Bean
    public Mongobee contextMongobee(){
        Mongobee runner = new Mongobee(loadConnectionString());
        runner.setChangeLogsScanPackage(MongoContextChangeLog.class.getPackageName());
        return runner;
    }

    @Bean
    public Mongobee attemptMongobee(){
        Mongobee runner = new Mongobee(loadConnectionString());
        runner.setChangeLogsScanPackage(MongoAttemptChangeLog.class.getPackageName());
        return runner;
    }

    @Bean
    public Mongobee identityMongobee(){
        Mongobee runner = new Mongobee(loadConnectionString());
        runner.setChangeLogsScanPackage(MongoIdentityChangeLog.class.getPackageName());
        return runner;
    }

    @Bean
    public ContextRepository contextRepository(JsonConverter jsonConverter, MongoDatabase database) {
        return MongoContextRepositoryConfig.builder()
                .jsonConverter(jsonConverter)
                .database(database)
                .build()
                .contextRepository();
    }

    @Bean
    public IdentityRepository identityRepository(AliasFactory aliasFactory, MongoDatabase database) {
        return MongoIdentityRepositoryConfig.builder()
                .aliasFactory(aliasFactory)
                .database(database)
                .build()
                .identityRepository();
    }

    @Bean
    public AttemptRepository attemptRepository(JsonConverter jsonConverter, MongoDatabase database) {
        return MongoAttemptRepositoryConfig.builder()
                .jsonConverter(jsonConverter)
                .database(database)
                .build()
                .attemptRepository();
    }

    @Bean
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(loadConnectionString()))
                .codecRegistry(codecRegistry)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient client) {
        return client.getDatabase(extractDatabaseName(loadConnectionString()));
    }

    private static String extractDatabaseName(String connectionString) {
        String[] tokens = connectionString.split("/");
        return tokens[tokens.length - 1];
    }

    private static String loadConnectionString() {
        return System.getProperty("spring.data.mongodb.uri");
    }

}
