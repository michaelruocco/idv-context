package uk.co.idv.app.manual.adapter.repository;

import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;

public interface RepositoryAdapter {

    IdentityRepository getIdentityRepository();

    ContextRepository getContextRepository();

    ContextPolicyRepository getContextPolicyRepository();

    AttemptRepository getAttemptRepository();

    LockoutPolicyRepository getLockoutPolicyRepository();

}
