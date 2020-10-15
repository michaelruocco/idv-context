package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.app.manual.adapter.repository.InMemoryRepositoryAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;

@Configuration
@Profile("stubbed")
public class SpringStubbedRepositoryConfig {

    @Bean
    public RepositoryAdapter repositoryAdapter() {
        return new InMemoryRepositoryAdapter();
    }

}
