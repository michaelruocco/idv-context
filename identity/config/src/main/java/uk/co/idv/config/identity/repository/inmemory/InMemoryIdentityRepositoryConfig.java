package uk.co.idv.config.identity.repository.inmemory;

import uk.co.idv.config.identity.repository.IdentityRepositoryConfig;
import uk.co.idv.context.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.context.usecases.identity.IdentityRepository;

public class InMemoryIdentityRepositoryConfig implements IdentityRepositoryConfig {

    private final IdentityRepository repository = new InMemoryIdentityRepository();

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

}
