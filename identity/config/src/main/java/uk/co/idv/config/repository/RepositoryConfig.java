package uk.co.idv.config.repository;

import uk.co.idv.context.usecases.identity.IdentityRepository;

public interface RepositoryConfig {

    IdentityRepository identityRepository();

}
