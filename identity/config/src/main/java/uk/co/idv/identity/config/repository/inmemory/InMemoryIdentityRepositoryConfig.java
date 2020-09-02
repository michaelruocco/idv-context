package uk.co.idv.identity.config.repository.inmemory;

import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

public class InMemoryIdentityRepositoryConfig implements IdentityRepositoryConfig {

    private final IdentityRepository repository = new InMemoryIdentityRepository();

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

}
