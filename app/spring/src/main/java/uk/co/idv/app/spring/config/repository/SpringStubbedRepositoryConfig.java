package uk.co.idv.app.spring.config.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.adapter.channel.ChannelAdapter;
import uk.co.idv.app.plain.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.plain.adapter.repository.RepositoryAdapter;

@Configuration
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
@Profile("stubbed")
public class SpringStubbedRepositoryConfig {

    @Bean
    public RepositoryAdapter repositoryAdapter() {
        return new InMemoryRepositoryAdapter();
    }

    @Bean
    public StubbedSetupPolicies setupPoliciesListener(Application application,
                                                    ChannelAdapter channelAdapter) {
        return StubbedSetupPolicies.builder()
                .application(application)
                .channelAdapter(channelAdapter)
                .build();
    }

}
