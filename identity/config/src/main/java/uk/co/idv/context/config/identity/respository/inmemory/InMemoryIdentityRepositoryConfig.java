package uk.co.idv.context.config.identity.respository.inmemory;

import uk.co.idv.context.config.identity.respository.IdentityRepositoryConfig;
import uk.co.idv.context.adapter.repository.InMemoryIdentityRepository;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

public class InMemoryIdentityRepositoryConfig implements IdentityRepositoryConfig {

    private final IdentityRepository repository = new InMemoryIdentityRepository();

    @Override
    public IdentityRepository identityRepository() {
        return repository;
    }

}
