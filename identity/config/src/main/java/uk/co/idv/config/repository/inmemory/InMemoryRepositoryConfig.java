package uk.co.idv.config.repository.inmemory;

import uk.co.idv.config.repository.RepositoryConfig;
import uk.co.idv.context.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.context.usecases.identity.IdentityRepository;

public class InMemoryRepositoryConfig implements RepositoryConfig {

    private final IdentityRepository repository = new InMemoryIdentityRepository();

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

}
