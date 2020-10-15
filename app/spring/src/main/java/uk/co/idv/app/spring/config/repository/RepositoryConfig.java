package uk.co.idv.app.spring.config.repository;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;

@Configuration
@Profile("!stubbed")
public class RepositoryConfig {

    @Bean
    public RepositoryAdapter repositoryAdapter(ContextRepository contextRepository,
                                               ContextPolicyRepository contextPolicyRepository,
                                               AttemptRepository attemptRepository,
                                               LockoutPolicyRepository lockoutPolicyRepository,
                                               IdentityRepository identityRepository) {
        return DefaultRepositoryAdapter.builder()
                .contextRepository(contextRepository)
                .contextPolicyRepository(contextPolicyRepository)
                .attemptRepository(attemptRepository)
                .lockoutPolicyRepository(lockoutPolicyRepository)
                .identityRepository(identityRepository)
                .build();
    }

    @Builder
    @Data
    private static class DefaultRepositoryAdapter implements RepositoryAdapter {

        private final ContextRepository contextRepository;
        private final ContextPolicyRepository contextPolicyRepository;
        private final AttemptRepository attemptRepository;
        private final LockoutPolicyRepository lockoutPolicyRepository;
        private final IdentityRepository identityRepository;

    }
}
